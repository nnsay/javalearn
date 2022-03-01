package com.neuralgalaxy.commons.visitor.converter;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 请求参数解析
 *
 * @author haiker
 */
@Slf4j
public class CurrentVisitorArgumentResolver implements HandlerMethodArgumentResolver {

    protected VisitorProperties config;

    public CurrentVisitorArgumentResolver(VisitorProperties config) {
        this.config = config;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentVisitor.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndView,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        CurrentVisitor currentVisitor = parameter.getParameterAnnotation(CurrentVisitor.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //判断是否是匿名用户
        boolean anonymous = authentication == null || (authentication instanceof AnonymousAuthenticationToken);
        //判断是否是必须验证
        boolean require = (currentVisitor.require() || currentVisitor.value());

        Asserts.isTrue(!require || (!anonymous && authentication.isAuthenticated()), GlobalErrors.UNAUTHORIZED);

        return anonymous ? config.getUserModel().getConstructor().newInstance() : authentication.getDetails();
    }
}
