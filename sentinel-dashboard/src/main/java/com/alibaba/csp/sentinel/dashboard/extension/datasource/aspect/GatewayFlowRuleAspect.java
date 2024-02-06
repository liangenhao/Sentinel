package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 网关限流规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class GatewayFlowRuleAspect extends AbstractRuleAsyncAspect {
    public GatewayFlowRuleAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.GW_FLOW, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchGatewayFlowRules(..))")
    public void fetchGatewayFlowRules() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.modifyGatewayFlowRules(..))")
    public void modifyGatewayFlowRules() {
    }

    @Override
    @Around("fetchGatewayFlowRules()")
    protected Object fetchRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRulesAsync(pjp);
    }

    @Override
    @Around("modifyGatewayFlowRules()")
    protected Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRules(pjp);
    }
}
