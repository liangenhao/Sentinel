package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 动态数据源规则方法切面抽象类
 *
 * @author enhao
 */
public abstract class AbstractRuleAspect {

    private static final Logger log = LoggerFactory.getLogger(AbstractRuleAspect.class);

    protected final RuleType ruleType;

    protected final DynamicRuleProcessorFactory factory;

    public AbstractRuleAspect(RuleType ruleType, DynamicRuleProcessorFactory factory) {
        this.ruleType = ruleType;
        this.factory = factory;
    }

    /**
     * 拉取规则信息
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return {@link List<RuleEntity>}
     * @throws Throwable Throwable
     */
    protected Object fetchRules(ProceedingJoinPoint pjp) throws Throwable {
        DynamicRuleProcessor<RuleEntity> processor = factory.getProcessor(ruleType);
        Object[] args = pjp.getArgs();
        String appName = (String) args[0];
        return processor.getRules(appName);
    }

    /**
     * 推送规则信息
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return {@link boolean}
     * @throws Throwable Throwable
     */
    protected Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        try {
            doPublishRules(pjp);
        } catch (Exception e) {
            Object[] args = pjp.getArgs();
            log.error("[RuleAspect] publish rules failed, appName:{}, rules: {}", args[0], args[3], e);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    protected void doPublishRules(ProceedingJoinPoint pjp) throws Throwable {
        DynamicRuleProcessor<RuleEntity> processor = factory.getProcessor(ruleType);
        Object[] args = pjp.getArgs();
        String appName = (String) args[0];
        List<RuleEntity> rules = (List<RuleEntity>) args[3];
        processor.publish(appName, rules);
    }
}
