package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.RuleType;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.config.DynamicDatasourceConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.ResolvableType;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * {@link NacosDynamicRuleProcessor} Bean 注册器
 *
 * @author enhao
 * @see NacosDynamicRuleProcessor
 */
public class NacosDynamicRuleProcessorRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String INFIX = "#";

    private static final String NACOS_DYNAMIC_RULE_PROCESSOR_BEAN_NAME = NacosDynamicRuleProcessor.class.getSimpleName();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        for (RuleType ruleType : RuleType.values()) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(NacosDynamicRuleProcessor.class)
                    // 按顺序指定构造参数
                    .addConstructorArgValue(ruleType)
                    .addConstructorArgReference(NacosDatasourceConfig.NACOS_CONFIG_SERVICE_BEAN_NAME)
                    .addConstructorArgReference(DynamicDatasourceConfig.DATASOURCE_CODEC_BEAN_NAME)
                    .addConstructorArgReference(getNacosDatasourcePropertiesBeanName());
            RootBeanDefinition beanDefinition = (RootBeanDefinition) builder.getBeanDefinition();

            // 指定 NacosDynamicRuleProcessor 类的泛型
            beanDefinition.setTargetType(ResolvableType.forClassWithGenerics(NacosDynamicRuleProcessor.class, ruleType.getClazz()));

            registry.registerBeanDefinition(getRuleProcessorBeanName(ruleType), beanDefinition);
        }
    }

    /**
     * 根据 {@link RuleType} 获取 {@link NacosDynamicRuleProcessor} Bean Name
     *
     * @param ruleType {@link RuleType}
     * @return {@link String}
     */
    private String getRuleProcessorBeanName(RuleType ruleType) {
        return ruleType.getName() + INFIX + NACOS_DYNAMIC_RULE_PROCESSOR_BEAN_NAME;
    }

    /**
     * 获取 {@link NacosDatasourceProperties} Bean Name
     * <p>
     * 通过 {@link org.springframework.boot.context.properties.ConfigurationProperties} 声明，
     * 且通过 {@link org.springframework.boot.context.properties.EnableConfigurationProperties} 注册到容器的外部化配置类
     * Bean 名称逻辑生成规则见
     * {@link org.springframework.boot.context.properties.ConfigurationPropertiesBeanRegistrar#getBeanName(Class)}
     * 即 {@link ConfigurationProperties#prefix()}-全类名
     *
     * @return {@link String}
     * @see org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar
     * @see org.springframework.boot.context.properties.ConfigurationPropertiesBeanRegistrar
     */
    private String getNacosDatasourcePropertiesBeanName() {
        String prefix = NacosDatasourceProperties.PREFIX;
        String fullClassName = NacosDatasourceProperties.class.getName();
        return (StringUtils.hasText(prefix) ? prefix + "-" + fullClassName : fullClassName);
    }
}
