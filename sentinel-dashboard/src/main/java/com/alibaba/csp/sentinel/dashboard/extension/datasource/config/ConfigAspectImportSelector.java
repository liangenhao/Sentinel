package com.alibaba.csp.sentinel.dashboard.extension.datasource.config;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect.AbstractConfigAspect;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * 配置切片导入选择器
 *
 * @author enhao
 */
public class ConfigAspectImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AssignableTypeFilter assignableTypeFilter = new AssignableTypeFilter(AbstractConfigAspect.class);
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(assignableTypeFilter);

        String basePackages = "com.alibaba.csp.sentinel.dashboard.extension.datasource.aspect";

        return scanner.findCandidateComponents(basePackages)
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .toArray(String[]::new);
    }
}