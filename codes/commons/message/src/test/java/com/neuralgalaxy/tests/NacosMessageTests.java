package com.neuralgalaxy.tests;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.neuralgalaxy.commons.message.MessageProperties;
import com.neuralgalaxy.commons.message.MessageService;
import com.neuralgalaxy.commons.message.nacos.MessageNacosProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Slf4j
@SpringBootTest(args = {
        "--spring.cloud.nacos.server-addr=nacos:8848",
        "--spring.cloud.nacos.password=nacos",
        "--spring.cloud.nacos.username=nacos",
        "--message.prefix=demo",
        "--message.nacos.data-id=testmessage"
})
@AutoConfigureMockMvc
public class NacosMessageTests {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageNacosProperties config;

    @Autowired
    MessageProperties messageConfig;

    @Autowired
    NacosConfigManager manager;

    @BeforeEach
    @SneakyThrows
    public void addData() throws NacosException {
        String content = String.format("%s.%s.%s=testiest",
                messageConfig.getPrefix(), Locale.CHINA.getLanguage(), "test");
        manager.getConfigService()
                .publishConfig(config.getDataId(), config.getGroup(), content);

        SettableFuture<String> future = SettableFuture.create();
        manager.getConfigService().addListener(config.getDataId(), config.getGroup(), new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                future.set(configInfo);
            }
        });
        future.get(7,TimeUnit.SECONDS);
    }

    @Test
    @SneakyThrows
    public void testOverWriteMessage() {
        Locale locale = Locale.CHINA;
        String message = messageService.getOverwriteMessage(locale, "test");
        Assertions.assertEquals("testiest", message);
    }

    @AfterEach
    @SneakyThrows
    public void removeData() {
        manager.getConfigService().removeConfig(config.getDataId(), config.getGroup());
    }
}
