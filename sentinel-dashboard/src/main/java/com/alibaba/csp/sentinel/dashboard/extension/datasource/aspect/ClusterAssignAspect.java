package com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppAssignResultVO;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicConfigOfListProcessor;
import com.alibaba.csp.sentinel.util.AssertUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Indexed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 集群流控-集群分配切面
 *
 * @author enhao
 */
@Aspect
@Indexed
public class ClusterAssignAspect extends AbstractConfigAspect {

    private final DynamicConfigOfListProcessor<ClusterAppAssignMap> clusterConfigProcessor;

    public ClusterAssignAspect(DynamicConfigOfListProcessor<ClusterAppAssignMap> clusterConfigProcessor) {
        this.clusterConfigProcessor = clusterConfigProcessor;
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.service.ClusterAssignService.applyAssignToApp(..))")
    public void applyAssignToApp() {
    }

    @Pointcut("execution(public * com.alibaba.csp.sentinel.dashboard.service.ClusterAssignService.unbindClusterServer(..))")
    public void unbindClusterServer() {
    }


    @Around("applyAssignToApp()")
    @SuppressWarnings("unchecked")
    public Object publishClusterAssignConfig(ProceedingJoinPoint pjp) throws Exception {
        Object[] args = pjp.getArgs();
        String appName = (String) args[0];
        List<ClusterAppAssignMap> clusterMapList = (List<ClusterAppAssignMap>) args[1];

        AssertUtil.assertNotBlank(appName, "app cannot be blank");
        AssertUtil.notNull(clusterMapList, "clusterMap cannot be null");

        List<ClusterAppAssignMap> clusterMap = clusterMapList.stream()
                .filter(Objects::nonNull)
                // .filter(ClusterAppAssignMap::getBelongToApp)
                .collect(Collectors.toList());

        clusterConfigProcessor.publishConfigs(appName, clusterMap);

        return new ClusterAppAssignResultVO()
                .setFailedClientSet(new HashSet<>())
                .setFailedServerSet(new HashSet<>());
    }

    @Around("unbindClusterServer()")
    public Object publishUnbindClusterServer(ProceedingJoinPoint pjp) throws Exception {
        Object[] args = pjp.getArgs();
        String appName = (String) args[0];
        String machineId = (String) args[1];

        AssertUtil.assertNotBlank(appName, "app cannot be blank");
        AssertUtil.assertNotBlank(machineId, "machineId cannot be blank");

        // todo cluster server not in app

        // cluster server in app
        clusterConfigProcessor.publishConfigs(appName, new ArrayList<>());

        return new ClusterAppAssignResultVO()
                .setFailedClientSet(new HashSet<>())
                .setFailedServerSet(new HashSet<>());
    }
}
