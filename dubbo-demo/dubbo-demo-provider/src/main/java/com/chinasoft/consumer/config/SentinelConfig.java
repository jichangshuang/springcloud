package com.chinasoft.consumer.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class SentinelConfig {


    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void init () {

        /**
         * 定义受保护的资源对象的规则
         */
        List<FlowRule> flowRules = new ArrayList<>();

        // 创建流控规则对象
        FlowRule flowRule = new FlowRule();
        // 设置流控规则 QPS
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源
        flowRule.setRefResource("");
        // 设置保护资源的阈值  1s 1次
        flowRule.setCount(1);

        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);


    }
}
