package com.neuralgalaxy.commons.asserts;

import javax.servlet.http.HttpServletResponse;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 &amp; 2016-06-24 15:37
 */
public interface GlobalErrors {

    AssertException BAD_GATEWAY = new AssertException(HttpServletResponse.SC_BAD_GATEWAY, "10502", "服务器开小差，稍后再试试！", "bad gateway!");

    /**
     * 系统运行时，意外报错，为保证错误不在网管层在向外扩延
     */
    AssertException SERVICE_UNAVAILABLE = new AssertException(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "10503", "服务器开小差，稍后再试试！", "Server deserted, try again later!");

    /**
     * 票根错误，重新登录
     */
    AssertException FORBIDDEN = new AssertException(HttpServletResponse.SC_FORBIDDEN, "10403", "您无权限访问", "Forbidden");

    /**
     * 未认证
     */
    AssertException UNAUTHORIZED = new AssertException(HttpServletResponse.SC_UNAUTHORIZED, "10401", "未登录！", "Unauthorized");
    /**
     * 登录超时
     */
    AssertException SESSION_TIMEOUT = new AssertException(HttpServletResponse.SC_UNAUTHORIZED, "11401", "登录超时！", "Session timeout");

    /**
     * 请求参数不合法
     */
    AssertException BAD_REQUEST = new AssertException(HttpServletResponse.SC_BAD_REQUEST, "10400", "请求参数错误，请检查后重试！", "Bad Request");


    AssertException SENTINEL_FLOW = new AssertException(HttpServletResponse.SC_NOT_ACCEPTABLE, "10801", "限流了", "Flow limit");
    AssertException SENTINEL_DEGRADE = new AssertException(HttpServletResponse.SC_NOT_ACCEPTABLE, "10802", "降级了", "Degrade");
    AssertException SENTINEL_PARAM_FLOW = new AssertException(HttpServletResponse.SC_NOT_ACCEPTABLE, "10803", "热点限流", "Param Flow");
    AssertException SENTINEL_SYSTEM_BLOCK = new AssertException(HttpServletResponse.SC_NOT_ACCEPTABLE, "10804", "系统规则（负载/...不满足要求）", "System Block");
    AssertException SENTINEL_AUTHORITY = new AssertException(HttpServletResponse.SC_NOT_ACCEPTABLE, "10805", "授权规则不通过", "authority");
}
