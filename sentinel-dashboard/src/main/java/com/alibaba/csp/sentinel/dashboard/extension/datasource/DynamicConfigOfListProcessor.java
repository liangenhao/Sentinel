package com.alibaba.csp.sentinel.dashboard.extension.datasource;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;

import java.util.List;

/**
 * 动态配置的列表数据源处理器
 *
 * @param <T>
 * @author enhao
 */
public abstract class DynamicConfigOfListProcessor<T> {

    protected final Class<T> genericType;

    protected final Codec<String, Object> codec;

    public DynamicConfigOfListProcessor(Class<T> genericType, Codec<String, Object> codec) {
        this.genericType = genericType;
        this.codec = codec;
    }

    public abstract List<T> getConfigs(String appName) throws Exception;

    public abstract void publishConfigs(String appName, List<T> configs) throws Exception;
}
