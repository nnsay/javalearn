package com.neuralgalaxy.commons.vault;

import com.neuralgalaxy.commons.vault.aliyun.AlibabaVaultAutoConfiguration;
import com.neuralgalaxy.commons.vault.aws.AwsVaultAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 安全密码字段获取
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(VaultProperties.class)
@Import({AlibabaVaultAutoConfiguration.class, AwsVaultAutoConfiguration.class})
public class VaultAutoConfiguration {

}
