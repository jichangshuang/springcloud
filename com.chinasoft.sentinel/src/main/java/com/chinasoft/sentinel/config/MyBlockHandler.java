package com.chinasoft.sentinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyBlockHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, BlockException ex) throws Exception {
        String msg = null;
        if (ex instanceof FlowException) {
            msg = "已被限流";
        } else if (ex instanceof DegradeException) {
            msg = "已被降级";
        } else if (ex instanceof ParamFlowException) {
            msg = "已被热点参数限流";
        } else if (ex instanceof SystemBlockException) {
            msg = "系统规则";
        } else if (ex instanceof AuthorityException) {
            msg = "授权规则";
        }

        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(response.getWriter(), msg);
    }
}
