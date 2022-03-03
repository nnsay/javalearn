package com.neuralgalaxy.commons.vault.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.TreeMap;


/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Data
@ConfigurationProperties(AlibabaVaultProperties.PREFIX)
public class AlibabaVaultProperties {
    public static final String PREFIX = "vault.alibaba";

    boolean enabled;

    String region = "cn-hangzhou";

    String accessKey;
    String secretAccessKey;

    String ecsMetadata;

    Map<String,String> keys = new TreeMap<>();
}
