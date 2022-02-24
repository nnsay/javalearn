package com.neuralgalaxy.commons.visitor.config;

import com.neuralgalaxy.commons.config.CommonsAutoConfiguration;
import com.neuralgalaxy.commons.visitor.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.VisitorStorage;
import com.neuralgalaxy.commons.visitor.converter.CurrentVisitorArgumentResolver;
import com.neuralgalaxy.commons.visitor.jwt.JwtVisitorSerializer;
import com.neuralgalaxy.commons.web.WebAutoConfiguration;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 &amp; 2019-06-24 15:43
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(VisitorProperties.class)
@AutoConfigureAfter({CommonsAutoConfiguration.class, WebAutoConfiguration.class})
public class VisitorAutoConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    String applicationName;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    public VisitorSerializer visitorSerializer(VisitorProperties config, @Autowired(required = false) VisitorStorage storage) throws Exception {
        return new JwtVisitorSerializer(config, applicationName, storage);
    }

    @Bean
    public CurrentVisitorArgumentResolver currentVisitorArgumentResolver(VisitorProperties config, @Autowired VisitorSerializer serializer) throws Exception {
        return new CurrentVisitorArgumentResolver(config, serializer);
    }

    @Override
    @SneakyThrows
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        CurrentVisitorArgumentResolver resolver = applicationContext.getBean(CurrentVisitorArgumentResolver.class);
        argumentResolvers.add(resolver);
    }
}
