package com.neuralgalaxy.stars.user.service;

import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.stars.StarsVisitorAutoConfiguration;
import com.neuralgalaxy.stars.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
@Slf4j
public class VisitorSerializerService implements VisitorSerializer, ApplicationRunner {

    private AuthService authService;

    @Autowired
    ApplicationContext context;

    @Override
    public String encode(Visitor visitor) {
        log.error("The consumer is not allowed to call this method to generate a token");
        //使用方不允许调用生成token，必须由stars模块生成
        throw GlobalErrors.FORBIDDEN;
    }

    @Override
    public Visitor decode(String ticket) {
        return authService.decodeToken(ticket);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authService = context.getBean(StarsVisitorAutoConfiguration.class).authService;
    }
}
