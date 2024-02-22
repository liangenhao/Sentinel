package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicConfigProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.lang.StringUtils;

/**
 * Nacos dynamic config processor.
 *
 * @param <T>
 * @author enhao
 */
public abstract class AbstractNacosDynamicConfigProcessor<T> extends DynamicConfigProcessor<T> {

    protected final ConfigService configService;

    protected final NacosDatasourceProperties properties;

    public AbstractNacosDynamicConfigProcessor(Class<T> genericType,
                                               ConfigService configService,
                                               Codec<String, Object> codec,
                                               NacosDatasourceProperties properties) {
        super(genericType, codec);
        this.configService = configService;
        this.properties = properties;
    }

    @Override
    public T getConfigs(String appName) throws Exception {
        String configs = configService.getConfig(getDataId(appName),
                properties.getGroup(), properties.getTimeout());
        if (StringUtils.isEmpty(configs)) {
            return genericType.getDeclaredConstructor().newInstance();
        }

        return codec.decode(configs, genericType);
    }

    @Override
    public void publishConfigs(String appName, T configs) throws Exception {
        if (configs == null) {
            return;
        }
        String dataId = getDataId(appName);
        String group = properties.getGroup();
        String encodedContent = codec.encode(configs);
        boolean success = configService.publishConfig(dataId, group, encodedContent, codec.getFormat().getType());
        if (!success) {
            String errorMsg = String.format("Nacos publish config failed, dataId=%s, group=%s, content=%s, type=%s",
                    dataId, group, encodedContent, codec.getFormat().getType());
            throw new RuntimeException(errorMsg);
        }
    }

    protected abstract String getDataId(String appName);
}
