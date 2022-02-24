package com.neuralgalaxy.commons.config;

import com.neuralgalaxy.commons.utilities.Spring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220221
 */
@Configuration(proxyBeanMethods = false)
public class CommonsAutoConfiguration {
    @Bean
    public Spring spring() {
        return new Spring();
    }
}
