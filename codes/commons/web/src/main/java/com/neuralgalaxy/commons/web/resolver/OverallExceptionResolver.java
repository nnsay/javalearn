package com.neuralgalaxy.commons.web.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuralgalaxy.commons.asserts.AssertException;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.message.MessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全体异常配置
 *
 * @author haiker
 */
@Slf4j
public class OverallExceptionResolver implements HandlerExceptionResolver {

    MessageService messageService;
    private ObjectMapper mapper;

    public OverallExceptionResolver(MessageService messageService, ObjectMapper mapper) {
        this.messageService = messageService;

        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
        builder.serializerByType(AssertException.class, new AssertExceptionJsonSerializer(this.messageService));
        if (mapper != null) {
            builder.configure(mapper);
        }
        this.mapper = builder.build();
    }

    @Override
    @SneakyThrows
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        if (e instanceof AssertException) {
            //do nothing
        } else {
            log.error("[OverallExceptionHandler] {}: {}", request.getRequestURI(), handler.getClass().getName(), e);
            e = GlobalErrors.SERVICE_UNAVAILABLE;
        }
        response.setStatus(((AssertException) e).getStatus());
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(), e);
        return new ModelAndView();
    }
}
