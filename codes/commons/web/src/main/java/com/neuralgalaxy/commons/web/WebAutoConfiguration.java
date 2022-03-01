package com.neuralgalaxy.commons.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuralgalaxy.commons.message.DefaultMessageService;
import com.neuralgalaxy.commons.message.MessageAutoConfiguration;
import com.neuralgalaxy.commons.message.MessageService;
import com.neuralgalaxy.commons.web.resolver.OverallExceptionResolver;
import com.neuralgalaxy.commons.web.sentinel.SentinelConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
@Slf4j
@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class, MessageAutoConfiguration.class})
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MessageService messageService() {
        log.info("not found message service use default");
        return new DefaultMessageService();
    }

    @Bean
    public OverallExceptionResolver overallExceptionResolver(
            @Autowired(required = false) MessageService messageService,
            @Autowired(required = false) ObjectMapper mapper
    ) {
        log.info("Enable overall exception resolver");
        return new OverallExceptionResolver(messageService, mapper);
    }

    @Bean
    public SentinelConfiguration sentinelConfiguration() {
        log.info("Enable sentinel block exception handler");
        return new SentinelConfiguration();
    }
}
