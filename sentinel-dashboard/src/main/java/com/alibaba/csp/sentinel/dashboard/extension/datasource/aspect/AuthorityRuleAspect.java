package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 访问控制规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class AuthorityRuleAspect extends AbstractRuleAspect {
    public AuthorityRuleAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.AUTHORITY, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchAuthorityRulesOfMachine(..))")
    public void fetchAuthorityRulesOfMachine() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.setAuthorityRuleOfMachine(..))")
    public void setAuthorityRuleOfMachine() {
    }

    @Override
    @Around("fetchAuthorityRulesOfMachine()")
    protected Object fetchRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRules(pjp);
    }

    @Override
    @Around("setAuthorityRuleOfMachine()")
    protected Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRules(pjp);
    }
}
