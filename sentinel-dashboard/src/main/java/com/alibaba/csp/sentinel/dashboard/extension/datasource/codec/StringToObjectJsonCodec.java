package com.alibaba.csp.sentinel.dashboard.extension.datasource.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.nacos.api.config.ConfigType;

import java.util.List;

/**
 * String to Object json codec.
 *
 * @author enhao
 */
public class StringToObjectJsonCodec implements Codec<String, Object> {

    public <T> List<T> decode(String input, Class<T> clazz) {
        return JSON.parseArray(input, clazz);
    }

    public String encode(Object output) {
        return JSON.toJSONString(output, SerializerFeature.PrettyFormat);
    }

    @Override
    public ConfigType getFormat() {
        return ConfigType.JSON;
    }
}
