package com.alibaba.csp.sentinel.dashboard.extension.datasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据源提供方外部化配置 {@link DatasourceProviderEnum}
 *
 * @author enhao
 */
@ConfigurationProperties(prefix = DatasourceProviderProperties.PREFIX)
public class DatasourceProviderProperties {

    public static final String PREFIX = "datasource";

    /**
     * 数据源提供方 {@link DatasourceProviderEnum}
     */
    private DatasourceProviderEnum provider = DatasourceProviderEnum.MEMORY;

    public DatasourceProviderEnum getProvider() {
        return provider;
    }

    public void setProvider(DatasourceProviderEnum provider) {
        this.provider = provider;
    }
}
