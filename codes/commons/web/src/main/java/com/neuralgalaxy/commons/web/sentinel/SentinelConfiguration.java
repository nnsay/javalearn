package com.neuralgalaxy.commons.web.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.neuralgalaxy.commons.asserts.AssertException;
import com.neuralgalaxy.commons.asserts.GlobalErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haiker
 */
public class SentinelConfiguration implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws AssertException {
        AssertException ae = GlobalErrors.SENTINEL_FLOW;

        // FlowException 限流异常
        if (e instanceof FlowException) {
            ae = GlobalErrors.SENTINEL_FLOW;
        }
        // DegradeException 降级异常
        else if (e instanceof DegradeException) {
            ae = GlobalErrors.SENTINEL_DEGRADE;
        }
        // ParamFlowException 参数限流异常
        else if (e instanceof ParamFlowException) {
            ae = GlobalErrors.SENTINEL_PARAM_FLOW;
        }
        // SystemBlockException 系统负载异常
        else if (e instanceof SystemBlockException) {
            ae = GlobalErrors.SENTINEL_SYSTEM_BLOCK;
        }
        // AuthorityException 授权异常
        else if (e instanceof AuthorityException) {
            ae = GlobalErrors.SENTINEL_AUTHORITY;
        }

        throw ae;
    }
}
