package com.alibaba.csp.sentinel.dashboard.extension.datasource;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos.NacosDatasourceProperties;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;

import java.util.List;

/**
 * 动态规则处理器，聚合 {@link DynamicRuleProvider} 和 {@link DynamicRulePublisher}
 *
 * @param <T> a subclass of {@link RuleEntity}
 * @author enhao
 */
public abstract class DynamicRuleProcessor<T extends RuleEntity> implements DynamicRuleProvider<List<T>>,
        DynamicRulePublisher<List<T>> {

    protected final RuleType ruleType;

    protected final Codec<String, Object> codec;

    protected DynamicRuleProcessor(RuleType ruleType, Codec<String, Object> codec) {
        this.ruleType = ruleType;
        this.codec = codec;
    }

    public RuleType getRuleType() {
        return ruleType;
    }
}
