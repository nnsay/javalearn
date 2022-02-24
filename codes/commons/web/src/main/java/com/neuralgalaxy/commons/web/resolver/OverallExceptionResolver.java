package com.neuralgalaxy.commons.web.resolver;

import com.neuralgalaxy.commons.asserts.AssertException;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 全体异常配置
 *
 * @author haiker
 */
@Slf4j
public class OverallExceptionResolver implements HandlerExceptionResolver {

    @Override
    @SneakyThrows
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        OutputStream out = resp.getOutputStream();
        if (ex instanceof AssertException) {
            //do nothing
        } else {
            log.error("{}", req.getRequestURI(), ex);
            ex = GlobalErrors.SERVICE_UNAVAILABLE;
        }
        resp.setStatus(((AssertException) ex).getStatus());
        resp.setContentType("application/json;charset=UTF-8");
        out.write(((AssertException) ex).json());
        return new ModelAndView();
    }
}
