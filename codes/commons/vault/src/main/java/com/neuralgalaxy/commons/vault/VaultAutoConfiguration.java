package com.neuralgalaxy.commons.vault;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.AlibabaCloudCredentials;
import com.aliyuncs.auth.AlibabaCloudCredentialsProvider;
import com.aliyuncs.auth.BasicCredentials;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.neuralgalaxy.commons.vault.aliyun.AliCloudVaultConfig;
import com.neuralgalaxy.commons.vault.aliyun.AliCloudVaultLocator;
import com.neuralgalaxy.commons.vault.aws.AwsVaultLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;

/**
 * 安全密码字段获取
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(VaultProperties.class)
public class VaultAutoConfiguration {

    @Autowired
    VaultProperties config;

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = "vault.aws.enabled", havingValue = "true")
    public class AwsVaultConfiguration {

        @Bean
        @ConditionalOnMissingBean({AwsCredentialsProvider.class, SsmClient.class})
        public AwsCredentialsProvider provider() {
            Assert.notNull(config.getAws(), "[vault] aws config not found!");
            AwsCredentialsProviderChain.Builder chain = AwsCredentialsProviderChain.builder();

            log.info("[vault] aws enable environment variable credentials provider");
            chain.addCredentialsProvider(EnvironmentVariableCredentialsProvider.create());

            if (StringUtils.hasText(config.getAws().getAccessKey())) {
                log.info("[vault] aws vault user static provider");
                AwsCredentials credentials = AwsBasicCredentials.create(config.getAws().getAccessKey(), config.getAws().getSecretAccessKey());
                AwsCredentialsProvider staticProvider = StaticCredentialsProvider.create(credentials);
                chain.addCredentialsProvider(staticProvider);
            }

            String profile = config.getAws().getProfile();
            chain.addCredentialsProvider(DefaultCredentialsProvider.builder().profileName(profile).build());
            return chain.build();
        }

        @Bean
        @ConditionalOnMissingBean
        public SsmClient ssmClient(VaultProperties config, AwsCredentialsProvider provider) {
            return SsmClient.builder()
                    .credentialsProvider(provider)
                    .region(Region.of(config.getAws().getRegion()))
                    .build();
        }

        @Bean
        public AwsVaultLocator awsVaultLocator(Environment environment, SsmClient ssmClient) {
            log.info("[vault] use aws vault");
            return new AwsVaultLocator(ssmClient, config.getAws().getKeys(), config.isAllowNotExists());
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = "vault.alicloud.enabled", havingValue = "true")
    public class AliCloudVaultConfiguration {

        @Bean
        public AlibabaCloudCredentialsProvider alibabaCloudCredentialsProvider() throws ClientException {
            if (StringUtils.hasText(config.getAlicloud().getAccessKey())) {
                AlibabaCloudCredentials credentials = new BasicCredentials(
                        config.getAlicloud().getAccessKey(), config.getAlicloud().getSecretAccessKey());
                com.aliyuncs.auth.DefaultCredentialsProvider
                        .addCredentialsProvider(new com.aliyuncs.auth.StaticCredentialsProvider(credentials));
            }
            return new com.aliyuncs.auth.DefaultCredentialsProvider();
        }

        @Bean
        @ConditionalOnMissingBean
        public IClientProfile clientProfile(AlibabaCloudCredentialsProvider provider) {
            AliCloudVaultConfig ali = config.getAlicloud();
            IClientProfile clientProfile = DefaultProfile.getProfile(ali.getRegion());
            clientProfile.setCredentialsProvider(provider);
            return clientProfile;
        }

        @Bean
        @ConditionalOnMissingBean
        public IAcsClient iAcsClient(IClientProfile clientProfile) {
            return new DefaultAcsClient(clientProfile);
        }

        @Bean
        public AliCloudVaultLocator aliCloudVaultLocator(IAcsClient client) {
            return new AliCloudVaultLocator(client, config.aws.getKeys(), config.allowNotExists);
        }
    }

}
