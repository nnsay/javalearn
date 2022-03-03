package com.neuralgalaxy.commons.vault.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Data
@ConfigurationProperties(AwsVaultProperties.PREFIX)
public class AwsVaultProperties {
    public static final String PREFIX = "vault.aws";
    /**
     * 是否启用vault插件，默认不启用
     */
    boolean enabled = false;

    String region = Region.US_EAST_1.id();

    String accessKey;
    String secretAccessKey;

    String profile;

    Map<String,String> keys = new TreeMap<>();
}
