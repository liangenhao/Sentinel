package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.stereotype.Indexed;

/**
 * Nacos dynamic processor for {@link ClusterAppAssignMap}
 *
 * @author enhao
 */
@Indexed
public class NacosDynamicClusterAssignMapProcessor extends AbstractNacosDynamicConfigOfListProcessor<ClusterAppAssignMap> {

    public static final String CLUSTER_MAP_POSTFIX = "-cluster-map";

    public NacosDynamicClusterAssignMapProcessor(ConfigService configService,
                                                 Codec<String, Object> codec,
                                                 NacosDatasourceProperties properties) {
        super(ClusterAppAssignMap.class, configService, codec, properties);
    }

    @Override
    protected String getDataId(String appName) {
        return appName + CLUSTER_MAP_POSTFIX;
    }
}
