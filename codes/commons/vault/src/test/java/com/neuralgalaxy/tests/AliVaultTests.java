package com.neuralgalaxy.tests;

import com.neuralgalaxy.commons.vault.VaultProperties;
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
        "--vault.alicloud.enabled=true",
        "--vault.alicloud.keys.db.password=/stack/${spring.application.name}/db_password",
        "--vault.alicloud.keys.jwt.secret=/stack/${spring.application.name}/jwt_secret",
        "--vault.alicloud.keys.is.maintenance=/stack/${spring.application.name}/maintenance",
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AliVaultTests {

    @Autowired
    ApplicationContext context;

    @Autowired
    VaultProperties config;

    @Test
    public void testDefault() {
        for (Map.Entry<String, String> entry : config.getAws().getKeys().entrySet()) {
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
