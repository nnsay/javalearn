package com.neuralgalaxy.commons.vault.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AwsVaultProperties.class)
@ConditionalOnProperty(value = "vault.aws.enabled", havingValue = "true")
public class AwsVaultAutoConfiguration {

    @Autowired
    AwsVaultProperties config;

    @Value("${vault.allow-not-exists:true}")
    boolean allowNotExists;

    @Bean
    @ConditionalOnMissingBean({AwsCredentialsProvider.class, SsmClient.class})
    public AwsCredentialsProvider provider() {
        AwsCredentialsProviderChain.Builder chain = AwsCredentialsProviderChain.builder();

        log.info("[vault] aws enable environment variable credentials provider");
        chain.addCredentialsProvider(EnvironmentVariableCredentialsProvider.create());

        if (StringUtils.hasText(config.getAccessKey())) {
            log.info("[vault] aws vault user static provider");
            AwsCredentials credentials = AwsBasicCredentials.create(config.getAccessKey(), config.getSecretAccessKey());
            AwsCredentialsProvider staticProvider = StaticCredentialsProvider.create(credentials);
            chain.addCredentialsProvider(staticProvider);
        }

        String profile = config.getProfile();
        chain.addCredentialsProvider(DefaultCredentialsProvider.builder().profileName(profile).build());
        return chain.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public SsmClient ssmClient(AwsVaultProperties config, AwsCredentialsProvider provider) {
        return SsmClient.builder()
                .credentialsProvider(provider)
                .region(Region.of(config.getRegion()))
                .build();
    }

    @Bean
    public AwsVaultLocator awsVaultLocator(Environment environment, SsmClient ssmClient) {
        log.info("[vault] use aws vault");
        return new AwsVaultLocator(ssmClient, config.getKeys(), allowNotExists);
    }
}
