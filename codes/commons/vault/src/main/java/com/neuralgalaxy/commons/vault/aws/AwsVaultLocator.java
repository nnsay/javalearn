package com.neuralgalaxy.commons.vault.aws;

import com.neuralgalaxy.commons.vault.AbstractVaultLocator;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.ParameterNotFoundException;
import software.amazon.awssdk.services.ssm.model.SsmException;

import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Slf4j
public class AwsVaultLocator extends AbstractVaultLocator {

    SsmClient ssmClient;

    public AwsVaultLocator(SsmClient ssmClient, Map<String, String> keys, boolean allowNotExists) {
        super(keys, allowNotExists);
        this.ssmClient = ssmClient;
    }

    @Override
    protected String getValue(String path, boolean allowNotExists) {
        try {
            GetParameterRequest request = GetParameterRequest.builder()
                    .name(path)
                    .withDecryption(true)
                    .build();
            GetParameterResponse response = ssmClient.getParameter(request);
            return response.parameter().value();
        } catch (SsmException e) {
            if (e instanceof ParameterNotFoundException && allowNotExists) {
                log.info("aws ssm path {} not found", path);
                return null;
            }
            throw e;
        }
    }
}
