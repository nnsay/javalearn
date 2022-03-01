package com.neuralgalaxy.commons.visitor.handler;

import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义无权访问处理类
 *
 * @author haiker
 */
@Slf4j
public class DeniedHandler implements AccessDeniedHandler {

    OverallExceptionResolver resolver;

    public DeniedHandler(OverallExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) {
        log.info("security denied: {}", e.getMessage());
        resolver.resolveException(request, response, this, GlobalErrors.FORBIDDEN);
    }
}
