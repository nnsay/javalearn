package com.neuralgalaxy.commons.visitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import com.neuralgalaxy.commons.visitor.config.VisitorWebSecurityConfiguration;
import com.neuralgalaxy.commons.visitor.converter.CurrentVisitorArgumentResolver;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.jwt.VisitorStorage;
import com.neuralgalaxy.commons.visitor.jwt.filter.JwtTokenFilter;
import com.neuralgalaxy.commons.visitor.jwt.serializer.JwtVisitorSerializer;
import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import com.neuralgalaxy.commons.visitor.role.PermissionHandler;
import com.neuralgalaxy.commons.web.WebAutoConfiguration;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 安全配置
 *
 * @author haiker
 */
@Slf4j
@Configuration
@Import(VisitorWebSecurityConfiguration.class)
@EnableConfigurationProperties(VisitorProperties.class)
@AutoConfigureAfter(WebAutoConfiguration.class)
public class VisitorAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    VisitorProperties config;

    @Autowired
    OverallExceptionResolver resolver;

    @Bean
    public CurrentVisitorArgumentResolver currentVisitorArgumentResolver() {
        return new CurrentVisitorArgumentResolver(config);
    }

    @Bean("sec")
    public PermissionHandler customerSecurityExpress(VisitorProperties config, PermissionChecker checker) {
        return new PermissionHandler(config.getApplicationName(), checker);
    }

    @Bean
    @ConditionalOnMissingBean
    public VisitorSerializer visitorSerializer(
            VisitorProperties config,
            @Autowired(required = false) VisitorStorage storage,
            @Autowired(required = false) ObjectMapper mapper
    ) {
        log.info("use default visitor serializer");
        return new JwtVisitorSerializer(config, storage, mapper);
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(VisitorSerializer visitorSerializer) {
        return new JwtTokenFilter(visitorSerializer, resolver);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentVisitorArgumentResolver());
    }

    /**
     * TOME Why? need this handler resolvers, if user {@link org.springframework.security.access.prepost.PreAuthorize}
     * @param resolvers
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, (request, response, handler, ex) -> {
            if (!(ex instanceof AccessDeniedException)) {
                return null;
            }
            resolver.resolveException(request, response, handler, GlobalErrors.FORBIDDEN);
            return new ModelAndView();
        });
    }
}
