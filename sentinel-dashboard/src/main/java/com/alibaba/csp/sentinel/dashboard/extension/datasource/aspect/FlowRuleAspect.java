package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 流控规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class FlowRuleAspect extends AbstractRuleAsyncAspect {

    public FlowRuleAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.FLOW, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchFlowRuleOfMachine(..))")
    public void fetchFlowRuleOfMachine() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.setFlowRuleOfMachine(..))")
    public void setFlowRuleOfMachine() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.setFlowRuleOfMachineAsync(..))")
    public void setFlowRuleOfMachineAsync() {
    }


    @Override
    @Around("fetchFlowRuleOfMachine()")
    public Object fetchRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRules(pjp);
    }

    @Override
    @Around("setFlowRuleOfMachine()")
    public Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRules(pjp);
    }

    @Override
    @Around("setFlowRuleOfMachineAsync()")
    protected Object publishRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRulesAsync(pjp);
    }
}
