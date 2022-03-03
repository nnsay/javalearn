package com.neuralgalaxy.commons.vault;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.neuralgalaxy.commons.vault.VaultProperties.PREFIX;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */

@Data
@ConfigurationProperties(PREFIX)
public class VaultProperties {
    public static final String PREFIX = "vault";

    /**
     * 是否允许不存在. 不命中
     */
    boolean allowNotExists = false;
}
