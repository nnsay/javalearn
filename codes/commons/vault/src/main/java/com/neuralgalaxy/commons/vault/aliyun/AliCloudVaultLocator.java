package com.neuralgalaxy.commons.vault.aliyun;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.kms.model.v20160120.GetSecretValueRequest;
import com.aliyuncs.kms.model.v20160120.GetSecretValueResponse;
import com.neuralgalaxy.commons.vault.AbstractVaultLocator;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220303
 */
@Slf4j
public class AliCloudVaultLocator extends AbstractVaultLocator {

    IAcsClient client;

    public AliCloudVaultLocator(IAcsClient client, Map<String, String> keys, boolean allowNotExists) {
        super(keys, allowNotExists);
        this.client = client;
    }

    @Override
    protected String getValue(String path, boolean allowNotExists) {
        GetSecretValueRequest request = new GetSecretValueRequest();
        request.setSecretName(path);
        request.setVersionStage("ACSCurrent");

        try {
            GetSecretValueResponse response = client.getAcsResponse(request);
            return response.getSecretData();
        } catch (ServerException e) {
            if ("Forbidden.ResourceNotFound".equals(e.getErrCode()) && allowNotExists) {
                return null;
            }
            throw new RuntimeException(e);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
}
