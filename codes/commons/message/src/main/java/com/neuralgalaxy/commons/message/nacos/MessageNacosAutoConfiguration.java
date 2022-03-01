package com.neuralgalaxy.commons.message.nacos;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.neuralgalaxy.commons.message.MessageProperties;
import com.neuralgalaxy.commons.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MessageNacosProperties.class)
@AutoConfigureAfter(NacosConfigAutoConfiguration.class)
@ConditionalOnProperty(value = "message.nacos.enabled", matchIfMissing = true, havingValue = "true")
public class MessageNacosAutoConfiguration {

    @Autowired
    MessageNacosProperties messageNacosProperties;
    @Autowired
    MessageProperties messageProperties;

    @Bean
    @ConditionalOnMissingBean
    public MessageService nacosMessageService(NacosConfigManager manager) throws NacosException {
        log.info("enable nacos message service");

        ConfigService service = manager.getConfigService();

        NacosMessageService messageService = new NacosMessageService();
        messageService.setProfix(messageProperties.getPrefix());

        String configInfo = service.getConfig(messageNacosProperties.getDataId(),
                messageNacosProperties.getGroup(), TimeUnit.SECONDS.toMillis(3));
        try {
            messageService.setMessageString(configInfo);
        } catch (IOException e) {
            log.error("pull nacos config file", e);
        }

        log.info("add nacos message changed listener");
        manager.getConfigService()
                .addListener(messageNacosProperties.getDataId(), messageNacosProperties.getGroup(), new AbstractListener() {
                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        log.info("[Listener] {} changed {}", messageNacosProperties.getDataId(), configInfo);
                        try {
                            messageService.setMessageString(configInfo);
                        } catch (IOException e) {
                            log.error("[Listener]", e);
                        }
                    }
                });
        return messageService;
    }
}
