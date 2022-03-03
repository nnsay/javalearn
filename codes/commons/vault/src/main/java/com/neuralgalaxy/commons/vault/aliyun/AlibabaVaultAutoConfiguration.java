package com.neuralgalaxy.commons.vault.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * @author haiker
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AlibabaVaultProperties.class)
@ConditionalOnProperty(value = "vault.alibaba.enabled", havingValue = "true")
public class AlibabaVaultAutoConfiguration {

    @Autowired
    AlibabaVaultProperties config;

    @Value("${vault.allow-not-exists:true}")
    boolean allowNotExists;

    @Bean
    @ConditionalOnMissingBean({IClientProfile.class, AlibabaCloudCredentialsProvider.class})
    public AlibabaCloudCredentialsProvider alibabaCloudCredentialsProvider() throws ClientException {
        AuthUtils.setEnvironmentECSMetaData(config.getEcsMetadata());
        if (StringUtils.hasText(config.getAccessKey())) {
            AlibabaCloudCredentials credentials =
                    new BasicCredentials(config.getAccessKey(), config.getSecretAccessKey());
            DefaultCredentialsProvider.addCredentialsProvider(new StaticCredentialsProvider(credentials));
        }
        return new DefaultCredentialsProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public IClientProfile clientProfile(AlibabaCloudCredentialsProvider provider) {
        IClientProfile clientProfile = DefaultProfile.getProfile(config.getRegion());
        clientProfile.setCredentialsProvider(provider);
        return clientProfile;
    }

    @Bean
    @ConditionalOnMissingBean
    public IAcsClient iAcsClient(IClientProfile clientProfile) {
        return new DefaultAcsClient(clientProfile);
    }

    @Bean
    public AlibabaVaultLocator aliCloudVaultLocator(IAcsClient client) {
        return new AlibabaVaultLocator(client, config.getKeys(), allowNotExists);
    }
}
