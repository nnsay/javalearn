package com.neuralgalaxy.commons.vault.aliyun;

import lombok.Data;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Data
public class AliCloudVaultConfig {

    String region = "cn-hangzhou";

    String accessKey;
    String secretAccessKey;
}
