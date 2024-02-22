package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicConfigOfListProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Nacos dynamic config of list Processor
 *
 * @param <T>
 * @author enhao
 */
public abstract class AbstractNacosDynamicConfigOfListProcessor<T> extends DynamicConfigOfListProcessor<T> {

    protected final ConfigService configService;

    protected final NacosDatasourceProperties properties;

    public AbstractNacosDynamicConfigOfListProcessor(Class<T> genericType,
                                                     ConfigService configService,
                                                     Codec<String, Object> codec,
                                                     NacosDatasourceProperties properties) {
        super(genericType, codec);
        this.configService = configService;
        this.properties = properties;
    }

    @Override
    public List<T> getConfigs(String appName) throws Exception {
        String configs = configService.getConfig(getDataId(appName),
                properties.getGroup(), properties.getTimeout());
        if (StringUtils.isEmpty(configs)) {
            return new ArrayList<>();
        }
        return codec.decodes(configs, genericType);
    }

    @Override
    public void publishConfigs(String appName, List<T> configs) throws Exception {
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
