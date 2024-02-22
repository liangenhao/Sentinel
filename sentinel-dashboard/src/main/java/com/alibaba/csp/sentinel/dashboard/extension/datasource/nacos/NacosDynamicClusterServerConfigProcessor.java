package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterServerModifyRequest;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.stereotype.Indexed;

/**
 * Nacos dynamic processor for {@link ClusterServerModifyRequest}
 *
 * @author enhao
 */
@Indexed
public class NacosDynamicClusterServerConfigProcessor extends AbstractNacosDynamicConfigProcessor<ClusterServerModifyRequest> {

    public static final String CLUSTER_SERVER_CONFIG_POSTFIX = "-cluster-server-config";

    public NacosDynamicClusterServerConfigProcessor(ConfigService configService,
                                                    Codec<String, Object> codec,
                                                    NacosDatasourceProperties properties) {
        super(ClusterServerModifyRequest.class, configService, codec, properties);
    }

    @Override
    protected String getDataId(String appName) {
        return appName + CLUSTER_SERVER_CONFIG_POSTFIX;
    }
}
