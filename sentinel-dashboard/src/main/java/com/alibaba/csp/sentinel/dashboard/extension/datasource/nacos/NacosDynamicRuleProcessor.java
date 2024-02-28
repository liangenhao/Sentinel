package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Nacos implementation of {@link DynamicRuleProcessor}
 *
 * @author enhao
 */
public class NacosDynamicRuleProcessor<T extends RuleEntity> extends DynamicRuleProcessor<T> {

    private final ConfigService configService;

    protected final NacosDatasourceProperties properties;

    public NacosDynamicRuleProcessor(RuleType ruleType,
                                     ConfigService configService,
                                     Codec<String, Object> codec,
                                     NacosDatasourceProperties properties) {
        super(ruleType, codec);
        this.configService = configService;
        this.properties = properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getRules(String appName) throws Exception {
        String rules = configService.getConfig(getDataId(appName, this.ruleType),
                properties.getGroup(),
                properties.getTimeout());
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return (List<T>) codec.decodes(rules, this.ruleType.getClazz());
    }

    @Override
    public void publish(String app, List<T> rules) throws Exception {
        if (rules == null) {
            return;
        }

        // 解决 sentinel 规则数据未持久化，导致服务重启后，推送规则导致历史规则数据丢失问题:
        // 先从数据源中查询出数据，然后添加本次的数据，最后推送
        // 存在问题：会把配置完全相同的规则给过滤掉
        List<T> allRules = Optional.ofNullable(getRules(app)).orElseGet(ArrayList::new);
        allRules.addAll(rules);
        // 根据 RuleEntity#id 去重，保留后面的
        allRules = new ArrayList<>(allRules.stream()
                .collect(Collectors.toMap(RuleEntity::getId, Function.identity(), (e1, e2) -> e2, LinkedHashMap::new))
                .values());
        // 根据 Rule 对象去重，保留后面的
        Map<Rule, T> ruleMap = allRules.stream()
                .collect(Collectors.toMap(RuleEntity::toRule, Function.identity(), (e1, e2) -> e2, LinkedHashMap::new));
        Collection<T> distinctRuleEntities = ruleMap.values();

        String dataId = getDataId(app, this.ruleType);
        String group = properties.getGroup();
        String encodedContent = codec.encode(distinctRuleEntities);
        boolean success = configService.publishConfig(dataId, group, encodedContent, codec.getFormat().getType());
        if (!success) {
            String errorMsg = String.format("Nacos publish config failed, dataId=%s, group=%s, content=%s, type=%s",
                    dataId, group, encodedContent, codec.getFormat().getType());
            throw new RuntimeException(errorMsg);
        }
    }

    private String getDataId(String appName, RuleType ruleType) {
        return appName + ruleType.getDataIdPostfix();
    }
}
