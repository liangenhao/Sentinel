package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;

import java.util.ArrayList;
import java.util.List;

/**
 * Nacos implementation of {@link DynamicRuleProcessor}
 *
 * @author enhao
 */
public class NacosDynamicRuleProcessor<T extends RuleEntity> extends DynamicRuleProcessor<T> {

    private final ConfigService configService;

    public NacosDynamicRuleProcessor(RuleType ruleType,
                                     ConfigService configService,
                                     Codec<String, Object> codec,
                                     NacosDatasourceProperties properties) {
        super(ruleType, codec, properties);
        this.configService = configService;
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
        return (List<T>) codec.decode(rules, this.ruleType.getClazz());
    }

    @Override
    public void publish(String app, List<T> rules) throws Exception {
        if (rules == null) {
            return;
        }
        String dataId = getDataId(app, this.ruleType);
        String group = properties.getGroup();
        String encodedContent = codec.encode(rules);
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
