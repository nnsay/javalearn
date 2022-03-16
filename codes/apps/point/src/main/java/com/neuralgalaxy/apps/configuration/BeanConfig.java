package com.neuralgalaxy.apps.configuration;

import com.neuralgalaxy.apps.bean.OSSToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean("ossToken")
    public OSSToken getOSSToken() {
        return new OSSToken("ak", "ks", "security key");
    }
}
