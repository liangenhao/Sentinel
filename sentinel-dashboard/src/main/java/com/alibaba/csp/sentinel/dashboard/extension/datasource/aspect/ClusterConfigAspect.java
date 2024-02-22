package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterClientModifyRequest;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterServerModifyRequest;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicConfigProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Indexed;

import java.util.concurrent.CompletableFuture;

/**
 * 集群流控-集群配置切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class ClusterConfigAspect extends AbstractConfigAspect {

    private static final Logger log = LoggerFactory.getLogger(ClusterConfigAspect.class);

    private final DynamicConfigProcessor<ClusterClientConfig> clientConfigProcessor;

    private final DynamicConfigProcessor<ClusterServerModifyRequest> serverConfigProcessor;

    public ClusterConfigAspect(DynamicConfigProcessor<ClusterClientConfig> clientConfigProcessor,
                               DynamicConfigProcessor<ClusterServerModifyRequest> serverConfigProcessor) {
        this.clientConfigProcessor = clientConfigProcessor;
        this.serverConfigProcessor = serverConfigProcessor;
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.service.ClusterConfigService.modifyClusterClientConfig(..))")
    public void modifyClusterClientConfig() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.service.ClusterConfigService.modifyClusterServerConfig(..))")
    public void modifyClusterServerConfig() {
    }

    @Around("modifyClusterClientConfig()")
    public Object publishClusterClientConfig(ProceedingJoinPoint pjp) throws Exception {
        Object[] args = pjp.getArgs();
        try {
            ClusterClientModifyRequest request = (ClusterClientModifyRequest) args[0];
            clientConfigProcessor.publishConfigs(request.getApp(), request.getClientConfig());
        } catch (Exception e) {
            log.error("[ClusterConfigAspect] async publish cluster client config failed, args={}", args, e);
            CompletableFuture<Void> future = new CompletableFuture<>();
            return future.completeExceptionally(e);
        }

        return CompletableFuture.completedFuture(null);
    }

    @Around("modifyClusterServerConfig()")
    public Object publishClusterServerConfig(ProceedingJoinPoint pjp) throws Exception {
        Object[] args = pjp.getArgs();
        try {
            ClusterServerModifyRequest request = (ClusterServerModifyRequest) args[0];
            serverConfigProcessor.publishConfigs(request.getApp(), request);
        } catch (Exception e) {
            log.error("[ClusterConfigAspect] async publish cluster server config failed, args={}", args, e);
            CompletableFuture<Void> future = new CompletableFuture<>();
            return future.completeExceptionally(e);
        }

        return CompletableFuture.completedFuture(null);
    }
}
