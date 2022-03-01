package com.neuralgalaxy.commons.message;

import com.neuralgalaxy.commons.message.nacos.MessageNacosAutoConfiguration;
import com.neuralgalaxy.commons.message.nacos.MessageNacosProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Slf4j
@Configuration
@Import(MessageNacosAutoConfiguration.class)
@EnableConfigurationProperties(MessageProperties.class)
public class MessageAutoConfiguration {

}
