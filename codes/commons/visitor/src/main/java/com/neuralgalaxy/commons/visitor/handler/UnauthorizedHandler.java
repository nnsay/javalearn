package com.neuralgalaxy.commons.visitor.handler;

import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 返回未登录信息
 *
 * @author haiker
 */
@Slf4j
public class UnauthorizedHandler implements AuthenticationEntryPoint, Serializable {

    OverallExceptionResolver resolver;

    public UnauthorizedHandler(OverallExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) {
        log.warn("Unauthorized : {}", e.getMessage());
        resolver.resolveException(request, response, this, GlobalErrors.UNAUTHORIZED);
    }
}
