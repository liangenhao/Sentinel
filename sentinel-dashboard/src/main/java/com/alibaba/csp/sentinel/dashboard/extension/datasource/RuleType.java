package com.alibaba.csp.sentinel.dashboard.extension.datasource;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;

/**
 * 规则类型
 *
 * @author enhao
 */
public enum RuleType {

    /**
     * 流控规则
     */
    FLOW("flow", "-flow-rules", FlowRuleEntity.class),
    /**
     * 熔断规则
     */
    DEGRADE("degrade", "-degrade-rules", DegradeRuleEntity.class),

    /**
     * 热点规则
     */
    PARAM_FLOW("param-flow", "-param-flow-rules", ParamFlowRuleEntity.class),

    /**
     * 系统规则
     */
    SYSTEM("system", "-system-rules", SystemRuleEntity.class),

    /**
     * 授权规则
     */
    AUTHORITY("authority", "-authority-rules", AuthorityRuleEntity.class),

    /**
     * 网关限流规则
     */
    GW_FLOW("gw-flow", "-gw-flow-rules", GatewayFlowRuleEntity.class),

    /**
     * 网关 api 分组规则
     */
    GW_API_GROUP("gw-api-group", "-gw-api-group-rules", ApiDefinitionEntity.class);

    private final String name;

    private final String dataIdPostfix;

    private final Class<? extends RuleEntity> clazz;

    RuleType(String name, String dataIdPostfix, Class<? extends RuleEntity> clazz) {
        this.name = name;
        this.dataIdPostfix = dataIdPostfix;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public String getDataIdPostfix() {
        return dataIdPostfix;
    }

    public Class<? extends RuleEntity> getClazz() {
        return clazz;
    }
}
