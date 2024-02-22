package com.alibaba.csp.sentinel.dashboard.extension.datasource;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;

/**
 * 动态配置处理器
 *
 * @param <T>
 * @author enhao
 */
public abstract class DynamicConfigProcessor<T> {

    protected final Class<T> genericType;

    protected final Codec<String, Object> codec;

    public DynamicConfigProcessor(Class<T> genericType, Codec<String, Object> codec) {
        this.genericType = genericType;
        this.codec = codec;
    }

    public abstract T getConfigs(String appName) throws Exception;

    public abstract void publishConfigs(String appName, T configs) throws Exception;
}
