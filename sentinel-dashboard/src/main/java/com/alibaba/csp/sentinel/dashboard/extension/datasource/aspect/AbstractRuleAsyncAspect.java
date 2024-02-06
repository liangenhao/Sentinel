package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * 动态数据源规则异步方法切面抽象类
 *
 * @author enhao
 */
public abstract class AbstractRuleAsyncAspect extends AbstractRuleAspect {

    private static final Logger log = LoggerFactory.getLogger(AbstractRuleAsyncAspect.class);

    public AbstractRuleAsyncAspect(RuleType ruleType, DynamicRuleProcessorFactory factory) {
        super(ruleType, factory);
    }

    /**
     * 异步拉取规则
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return {@link CompletableFuture<com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity>}
     * @throws Throwable Throwable
     */
    protected Object fetchRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object rules = super.fetchRules(pjp);
            return CompletableFuture.completedFuture(rules);
        } catch (Exception e) {
            Object[] args = pjp.getArgs();
            log.error("[RuleAsyncAspect] async fetch rules failed, appName={}", args[0], e);
            CompletableFuture<Void> future = new CompletableFuture<>();
            return future.completeExceptionally(e);
        }
    }

    /**
     * 异步推送规则
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return {@link CompletableFuture<Void>}
     * @throws Throwable Throwable
     */
    protected Object publishRulesAsync(ProceedingJoinPoint pjp) throws Throwable {
        try {
            // 考虑用户需要明确知道是否推送成功，这里不采用真实的异步推送
            // 并且操作频率不会很高，所以这里直接同步执行
            // 这么写是为了兼容 SentinelApiClient 中的方法签名
            super.doPublishRules(pjp);
        } catch (Exception e) {
            Object[] args = pjp.getArgs();
            log.error("[RuleAsyncAspect] async publish rules failed, appName={}, rules={}", args[0], args[3], e);
            CompletableFuture<Void> future = new CompletableFuture<>();
            return future.completeExceptionally(e);
        }
        return CompletableFuture.completedFuture(null);
    }

}
