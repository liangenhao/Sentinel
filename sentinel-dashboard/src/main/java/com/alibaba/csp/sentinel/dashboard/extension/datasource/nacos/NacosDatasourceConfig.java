package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.config.DatasourceProviderProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Nacos 数据源配置 {@link NacosDatasourceProperties}
 */
@Configuration
@EnableConfigurationProperties({DatasourceProviderProperties.class, NacosDatasourceProperties.class})
@ConditionalOnProperty(prefix = DatasourceProviderProperties.PREFIX, name = "provider", havingValue = "NACOS")
@Import({NacosDynamicRuleProcessorRegistrar.class, NacosDynamicConfigProcessorImportSelector.class})
public class NacosDatasourceConfig {

    private static final Logger log = LoggerFactory.getLogger(NacosDatasourceConfig.class);

    public static final String NACOS_CONFIG_SERVICE_BEAN_NAME = "nacosConfigService";

    @Bean(name = NACOS_CONFIG_SERVICE_BEAN_NAME)
    public ConfigService nacosConfigService(NacosDatasourceProperties properties) throws NacosException {
        ConfigService configService = NacosFactory.createConfigService(properties.assembleConfigServiceProperties());
        log.info("Sentinel Dynamic DataSource is [Nacos], Nacos ConfigService registered");
        return configService;
    }

}
