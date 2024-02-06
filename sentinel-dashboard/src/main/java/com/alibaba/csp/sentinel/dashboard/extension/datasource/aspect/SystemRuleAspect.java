package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 系统保护规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class SystemRuleAspect extends AbstractRuleAspect {

    public SystemRuleAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.SYSTEM, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchSystemRuleOfMachine(..))")
    public void fetchSystemRuleOfMachine() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.setSystemRuleOfMachine(..))")
    public void setSystemRuleOfMachine() {
    }

    @Override
    @Around("fetchSystemRuleOfMachine()")
    protected Object fetchRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRules(pjp);
    }

    @Override
    @Around("setSystemRuleOfMachine()")
    protected Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRules(pjp);
    }
}
