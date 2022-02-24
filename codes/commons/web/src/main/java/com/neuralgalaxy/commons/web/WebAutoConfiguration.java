package com.neuralgalaxy.commons.web;

import com.neuralgalaxy.commons.web.fastjson.FastJsonConfigurer;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import com.neuralgalaxy.commons.web.sentinel.SentinelConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WebAutoConfiguration {

    @Bean
    public FastJsonConfigurer fastjsonConfigurer() {
        log.info("Enable fastjson converters");
        return new FastJsonConfigurer();
    }

    @Bean
    public OverallExceptionResolver overallExceptionResolver() {
        log.info("Enable overall exception resolver");
        return new OverallExceptionResolver();
    }

    @Bean
    public SentinelConfiguration sentinelConfiguration() {
        log.info("Enable sentinel block exception handler");
        return new SentinelConfiguration();
    }
}
