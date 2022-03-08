package com.neuralgalaxy.commons.vault.aws;

import lombok.Data;
import software.amazon.awssdk.regions.Region;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Data
public class AwsVaultConfig {

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
