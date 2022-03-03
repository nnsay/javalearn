package com.neuralgalaxy.tests;

import com.neuralgalaxy.commons.vault.aliyun.AlibabaVaultProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@SpringBootTest(args = {
        "--spring.application.name=test2",
        "--vault.allow-not-exists=true",
        "--vault.alibaba.enabled=true",
        "--vault.alibaba.keys.db.password=/stack/${spring.application.name}/db_password",
        "--vault.alibaba.keys.jwt.secret=/stack/${spring.application.name}/jwt_secret",
        "--vault.alibaba.keys.is.maintenance=/stack/${spring.application.name}/maintenance",
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AliVaultTests {

    @Autowired
    ApplicationContext context;

    @Autowired
    AlibabaVaultProperties config;

    @Test
    public void testDefault() {
        for (Map.Entry<String, String> entry : config.getKeys().entrySet()) {
            String key = entry.getKey();
            Assertions.assertTrue(context.getEnvironment().containsProperty(key), "not contains key: " + key);
        }
    }

    @Test
    public void testNotfound() {
        String value = context.getEnvironment().getProperty("jenkins.notfound.password");
        Assertions.assertNull(value, " jenkins.notfound.password is not null");
    }
}
