package com.neuralgalaxy.commons.web.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuralgalaxy.commons.asserts.AssertException;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.utilities.Locales;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;

/**
 * 全体异常配置
 *
 * @author haiker
 */
@Slf4j
public class OverallExceptionResolver implements HandlerExceptionResolver {

    private MessageService messageService;
    private ObjectMapper om = Jackson2ObjectMapperBuilder.json().build();

    public OverallExceptionResolver(MessageService messageService) {
        this.messageService = messageService;
    }

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

        String code = ((AssertException) ex).getCode();
        String message = ex.getMessage();
        if (messageService != null) {
            Locale locale = Locales.mustGet();
            String messageOverwrite = messageService.getOverwriteMessage(locale, code);
            if (StringUtils.hasText(messageOverwrite)) {
                message = messageOverwrite;
            }
        }
        byte[] json = om.writerWithDefaultPrettyPrinter()
                .writeValueAsBytes(Map.of("code", code, "message", message));
        out.write(json);

        return new ModelAndView();
    }
}
