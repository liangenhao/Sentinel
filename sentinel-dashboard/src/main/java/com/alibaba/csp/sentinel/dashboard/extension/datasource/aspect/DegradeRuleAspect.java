package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 熔断降级规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class DegradeRuleAspect extends AbstractRuleAspect {

    public DegradeRuleAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.DEGRADE, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchDegradeRuleOfMachine(..))")
    public void fetchDegradeRuleOfMachine() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.setDegradeRuleOfMachine(..))")
    public void setDegradeRuleOfMachine() {
    }

    @Override
    @Around("fetchDegradeRuleOfMachine()")
    protected Object fetchRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRules(pjp);
    }

    @Override
    @Around("setDegradeRuleOfMachine()")
    protected Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRules(pjp);
    }
}
