package com.alibaba.csp.sentinel.dashboard.extension.datasource;

import com.alibaba.csp.sentinel.dashboard.client.CommandFailedException;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 动态规则处理器工厂 {@link DynamicRuleProcessor}
 */
public class DynamicRuleProcessorFactory {

    private final Map<RuleType, DynamicRuleProcessor<? extends RuleEntity>> processorMap;

    public DynamicRuleProcessorFactory(List<DynamicRuleProcessor<? extends RuleEntity>> processors) {
        processorMap = processors.stream()
                .collect(Collectors.toMap(DynamicRuleProcessor::getRuleType, Function.identity(), (p1, p2) -> p1));

        EnumSet<RuleType> ruleTypeSet = EnumSet.allOf(RuleType.class);
        if (!processorMap.keySet().containsAll(ruleTypeSet)) {
            ruleTypeSet.removeAll(processorMap.keySet());
            throw new IllegalStateException("Not found DynamicRuleProcessor Bean Class for rule type: " + ruleTypeSet);
        }
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public <T extends RuleEntity> DynamicRuleProcessor<T> getProcessor(RuleType ruleType) {
        DynamicRuleProcessor<T> processor = (DynamicRuleProcessor<T>) processorMap.get(ruleType);
        if (null == processor) {
            throw new CommandFailedException(ruleType.getName() + " DynamicRuleProcessor Bean not found");
        }
        return processor;
    }
}
