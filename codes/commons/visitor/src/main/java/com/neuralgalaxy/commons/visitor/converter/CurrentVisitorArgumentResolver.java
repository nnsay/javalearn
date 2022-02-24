package com.neuralgalaxy.commons.visitor.converter;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * 请求参数解析
 *
 * @author haiker
 */
@Slf4j
public class CurrentVisitorArgumentResolver implements HandlerMethodArgumentResolver {

    protected VisitorProperties config;
    protected VisitorSerializer visitorSerializer;

    public CurrentVisitorArgumentResolver(VisitorProperties config, VisitorSerializer visitorSerializer) throws Exception {
        this.config = config;
        this.visitorSerializer = visitorSerializer;
        this.check(config.getEntityClassName());
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentVisitor.class);
    }

    /**
     * 检查实体类是否满足要求
     *
     * @param entityClass 实体类型
     * @throws Exception 验证失败将抛出异常
     */
    private void check(Class<? extends Visitor> entityClass) throws Exception {
        Assert.notNull(entityClass,"[visitor] entityClassName not found!");
        for (Constructor<?> c : entityClass.getConstructors()) {
            if (c.getParameterCount() == 0 && Modifier.isPublic(c.getModifiers())) {
                return;
            }
        }
        throw new IllegalClassFormatException("entityClassName class not found init method");
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndView,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CurrentVisitor currentVisitor = parameter.getParameterAnnotation(CurrentVisitor.class);
        boolean require = (currentVisitor.require() || currentVisitor.value());

        Visitor ticket = config.getEntityClassName().getConstructor().newInstance();

        String cookieName = config.getCookieName();
        String ticketHeader = webRequest.getHeader(config.getCookieName());

        //没有在header内容中，从参数总获取
        if (!StringUtils.hasText(ticketHeader)) {
            ticketHeader = webRequest.getParameter(cookieName);
        }
        if (StringUtils.hasText(ticketHeader)) {
            ticket = visitorSerializer.decode(ticketHeader.substring(7));
        }

        Asserts.isTrue(!require || !ticket.isAnonymous(), GlobalErrors.UNAUTHORIZED);

        webRequest.setAttribute(Visitor.REQUEST_ATTRIBUTE_NAME, ticket, NativeWebRequest.SCOPE_REQUEST);
        return ticket;
    }
}
