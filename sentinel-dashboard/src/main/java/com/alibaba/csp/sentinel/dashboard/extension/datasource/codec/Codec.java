package com.alibaba.csp.sentinel.dashboard.extension.datasource.codec;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
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

    <T> List<T> decode(INPUT input, Class<T> clazz);

    INPUT encode(OUTPUT output);

    ConfigType getFormat();

}
