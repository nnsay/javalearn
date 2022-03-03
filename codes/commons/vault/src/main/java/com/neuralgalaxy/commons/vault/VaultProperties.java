package com.neuralgalaxy.commons.vault;

import com.neuralgalaxy.commons.vault.aliyun.AliCloudVaultConfig;
import com.neuralgalaxy.commons.vault.aws.AwsVaultConfig;
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
     * 阿里云配置
     */
    AliCloudVaultConfig alicloud = new AliCloudVaultConfig();

    /**
     * 亚马逊云配置
     */
    AwsVaultConfig aws = new AwsVaultConfig();

    /**
     * 是否允许不存在. 不命中
     */
    boolean allowNotExists = false;
}
