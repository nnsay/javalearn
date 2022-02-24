package com.neuralgalaxy.commons.visitor.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.VisitorStorage;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */
@Slf4j
public class JwtVisitorSerializer implements VisitorSerializer {

    /**
     * jwt 算法
     */
    Algorithm algorithm;
    JWTVerifier verifier;
    /**
     * 应用名称
     */
    String applicationName;

    VisitorProperties config;

    VisitorStorage storage;

    public JwtVisitorSerializer(VisitorProperties config, String applicationName, VisitorStorage storage) throws Exception {
        this.config = config;
        this.applicationName = applicationName;
        this.storage = storage;

        VisitorProperties.Jwt jwt = config.getJwt();
        Assert.notNull(jwt, "[visitor] jwt config not found!");

        this.algorithm = Algorithm.HMAC256(config.getJwt().getSecret());
        this.verifier = JWT.require(this.algorithm).withIssuer(this.applicationName).build();
        if (this.storage == null) {
            log.info("jwt token storage is empty");
        }
    }

    /**
     * 创建一个 keyId
     *
     * @return keyId
     */
    public String createKeyId() {
        return UUID.randomUUID().toString();
    }

    public void setExpireTime(JWTCreator.Builder builder) {
        //设置过期时间
        if (config.getJwt().getExpireSecond() > -1) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.SECOND, config.getJwt().getExpireSecond());
            builder.withExpiresAt(now.getTime());
        }
    }

    @Override
    public String encode(Visitor visitor) {
        String keyId = createKeyId();

        JWTCreator.Builder builder = JWT.create()
                //设置编发者
                .withIssuer(this.applicationName)
                .withKeyId(keyId);

        setExpireTime(builder);

        String token = builder
                .withPayload(JSON.parseObject(JSON.toJSONString(visitor)))
                .sign(algorithm);

        if (this.storage != null) {
            this.storage.save(visitor, keyId, token);
        }
        return token;
    }

    @Override
    public Visitor decode(String ticket) {
        DecodedJWT jwt;
        try {
            jwt = this.verifier.verify(ticket);
        }
        //session timeout
        catch (TokenExpiredException e) {
            throw GlobalErrors.SESSION_TIMEOUT;
        } catch (JWTVerificationException e) {
            throw GlobalErrors.FORBIDDEN;
        }

        byte[] payload = Base64.getDecoder().decode(jwt.getPayload());
        Visitor visitor = JSON.parseObject(payload, this.config.getEntityClassName());

        String keyId = jwt.getKeyId();
        Asserts.isTrue(this.storage == null || this.storage.verify(visitor, keyId),
                GlobalErrors.SESSION_TIMEOUT);

        return visitor;
    }
}
