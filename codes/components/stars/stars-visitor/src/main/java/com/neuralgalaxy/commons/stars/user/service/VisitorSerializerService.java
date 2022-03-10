package com.neuralgalaxy.commons.stars.user.service;

import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.stars.user.api.AuthClient;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
@Slf4j
public class VisitorSerializerService implements VisitorSerializer {


    AuthClient authClient;

    public VisitorSerializerService(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public String encode(Visitor visitor) {
        log.error("The consumer is not allowed to call this method to generate a token");
        //使用方不允许调用生成token，必须由stars模块生成
        throw GlobalErrors.FORBIDDEN;
    }

    @Override
    public Visitor decode(String ticket) {
        return authClient.decodeUserToken(ticket);
    }

}
