package com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos;

import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicConfigOfListProcessor;
import com.alibaba.csp.sentinel.dashboard.extension.datasource.DynamicConfigProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * Nacos dynamic config processor import selector.
 *
 * @author enhao
 */
public class NacosDynamicConfigProcessorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(DynamicConfigProcessor.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(DynamicConfigOfListProcessor.class));

        String basePackages = "com.alibaba.csp.sentinel.dashboard.extension.datasource.nacos";

        return scanner.findCandidateComponents(basePackages)
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .toArray(String[]::new);
    }
}
