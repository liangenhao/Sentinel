package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

/**
 * 用户自定义的 API 定义分组规则切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class GatewayApiAspect extends AbstractRuleAsyncAspect {

    public GatewayApiAspect(DynamicRuleProcessorFactory factory) {
        super(RuleType.GW_API_GROUP, factory);
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.fetchApis(..))")
    public void fetchApis() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient.modifyApis(..))")
    public void modifyApis() {
    }

    @Override
    @Around("fetchApis()")
    protected Object fetchRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        return super.fetchRulesAsync(pjp);
    }

    @Override
    @Around("modifyApis()")
    protected Object publishRules(ProceedingJoinPoint pjp) throws Throwable {
        return super.publishRules(pjp);
    }
}
