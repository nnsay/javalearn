package com.neuralgalaxy.commons.visitor.jwt.serializer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.jwt.VisitorStorage;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */
@Slf4j
public class JwtVisitorSerializer implements VisitorSerializer {

    Algorithm algorithm;
    JWTVerifier verifier;

    VisitorProperties config;

    VisitorStorage storage;

    ObjectMapper mapper;

    public JwtVisitorSerializer(VisitorProperties config, VisitorStorage storage, ObjectMapper mapper) {
        this.config = config;
        this.storage = storage;
        if (this.storage == null) {
            log.info("jwt token storage is empty");
        }

        VisitorProperties.Jwt jwt = config.getJwt();
        Assert.notNull(jwt, "[visitor] jwt config not found!");

        this.algorithm = Algorithm.HMAC256(jwt.getSecret());
        this.verifier = JWT.require(this.algorithm).build();

        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
        if (mapper != null) {
            builder.configure(mapper);
        }
        builder.failOnUnknownProperties(false);
        this.mapper = builder.build();
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

        JWTCreator.Builder builder = JWT.create().withKeyId(keyId);
        setExpireTime(builder);

        Map<String, ?> payload = mapper.convertValue(visitor, Map.class);
        String token = builder.withPayload(payload).sign(algorithm);

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
        Visitor userModel = null;
        try {
            userModel = mapper.readValue(payload, this.config.getUserModel());
        } catch (IOException e) {
            log.error("reader jwt payload error", e);
            throw GlobalErrors.SERVICE_UNAVAILABLE;
        }

        String keyId = jwt.getKeyId();

        Asserts.isTrue(this.storage == null || this.storage.verify(userModel, keyId), GlobalErrors.SESSION_TIMEOUT);

        return userModel;
    }
}
