package com.alibaba.csp.sentinel.dashboard.extension.datasource.config;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicRuleProcessorFactory;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.Codec;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.codec.StringToObjectJsonCodec;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * 动态数据源配置
 *
 * @author enhao
 */
@Configuration
@ConditionalOnExpression("T(com.alibaba.csp.sentinel.dashboard.extension.datasource.config.DatasourceProviderEnum).valueOf('${datasource.provider:MEMORY}') != T(com.alibaba.csp.sentinel.dashboard.extension.datasource.config.DatasourceProviderEnum).MEMORY")
@Import({RuleAspectImportSelector.class, ConfigAspectImportSelector.class})
@EnableAspectJAutoProxy(exposeProxy = true)
public class DynamicDatasourceConfig {

    public static final String DATASOURCE_CODEC_BEAN_NAME = "datasourceCodec";

    @Bean
    public DynamicRuleProcessorFactory dynamicRuleProcessorFactory(
            List<DynamicRuleProcessor<? extends RuleEntity>> processors) {
        return new DynamicRuleProcessorFactory(processors);
    }

    @Bean(name = DATASOURCE_CODEC_BEAN_NAME)
    @ConditionalOnMissingBean
    public Codec<String, Object> stringObjectJsonCodec() {
        return new StringToObjectJsonCodec();
    }

}
