package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 热点参数规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class ParamRuleAspect extends AbstractRuleAsyncAspect {

    public ParamRuleAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.PARAM_FLOW, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchParamFlowRulesOfMachine(..))")
    public void fetchParamFlowRulesOfMachine() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.setParamFlowRuleOfMachine(..))")
    public void setParamFlowRuleOfMachine() {
    }

    @Override
    @Around("fetchParamFlowRulesOfMachine()")
    protected Object fetchRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRulesAsync(pjp);
    }

    @Override
    @Around("setParamFlowRuleOfMachine()")
    protected Object publishRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRulesAsync(pjp);
    }
}
