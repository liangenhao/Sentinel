package com.alibaba.csp.sentinel.dashboard.extension.datasource.codec;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.config.ConfigType;

import java.util.List;

/**
 * 编解码器
 *
 * @param <INPUT>  输入
 * @param <OUTPUT> 输出
 * @author enhao
 */
public interface Codec<INPUT, OUTPUT> {

    <T> List<T> decodes(INPUT input, Class<T> clazz);

    <T> T decode(INPUT input, Class<T> clazz);

    <T> T decode(INPUT input, TypeReference<T> type);

    INPUT encode(OUTPUT output);

    ConfigType getFormat();

}
