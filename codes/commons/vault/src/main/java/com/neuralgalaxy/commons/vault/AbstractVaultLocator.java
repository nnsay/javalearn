package com.neuralgalaxy.commons.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Slf4j
@Order(100)
public abstract class AbstractVaultLocator implements PropertySourceLocator {

    protected Map<String, String> keys;
    protected boolean allowNotExists;

    public AbstractVaultLocator(Map<String, String> keys, boolean allowNotExists) {
        this.keys = keys;
        this.allowNotExists = allowNotExists;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        Map<String, Object> values = new HashMap<>();

        for (Map.Entry<String, String> entry : this.keys.entrySet()) {
            String key = entry.getKey();
            String path = environment.resolvePlaceholders(entry.getValue());
            log.info("[vault] fetch {} from {}", key, path);
            String value = getValue(path, allowNotExists);
            Assert.isTrue(allowNotExists || StringUtils.hasText(value),
                    String.format("%s not found in %s", key, value));
            values.put(key, value);
        }
        return new MapPropertySource("vault", values);
    }

    protected abstract String getValue(String path, boolean allowNotExists);
}
