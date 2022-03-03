package com.neuralgalaxy.tests;

import com.neuralgalaxy.commons.vault.VaultProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Map;

@SpringBootTest(args = {
        "--spring.application.name=cicd",
        "--vault.allow-not-exists=true",
        "--vault.aws.enabled=true",
        "--vault.aws.keys.jenkins.dev.password=/common/${spring.application.name}/jenkins/dev_password",
        "--vault.aws.keys.jenkins.admin.password=/common/${spring.application.name}/jenkins/admin_password",
        "--vault.aws.keys.jenkins.notfound.password=/common/${spring.application.name}/jenkins/notfound",
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AwsVaultTests {

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
