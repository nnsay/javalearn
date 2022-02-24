package com.neuralgalaxy.stars.users.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(UserConfiguration.class)
public class UserAutoConfiguration {

}
