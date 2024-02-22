package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.stereotype.Indexed;

/**
 * Nacos dynamic processor for {@link ClusterClientConfig}
 *
 * @author enhao
 */
@Indexed
public class NacosDynamicClusterClientConfigProcessor extends AbstractNacosDynamicConfigProcessor<ClusterClientConfig> {

    public static final String CLUSTER_CLIENT_CONFIG_POSTFIX = "-cluster-client-config";

    public NacosDynamicClusterClientConfigProcessor(ConfigService configService,
                                                    Codec<String, Object> codec,
                                                    NacosDatasourceProperties properties) {
        super(ClusterClientConfig.class, configService, codec, properties);
    }

    @Override
    protected String getDataId(String appName) {
        return appName + CLUSTER_CLIENT_CONFIG_POSTFIX;
    }
}
