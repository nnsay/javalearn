package com.neuralgalaxy.stars;

import com.neuralgalaxy.commons.visitor.VisitorAutoConfiguration;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import com.neuralgalaxy.stars.auth.model.UserTokenModel;
import com.neuralgalaxy.stars.auth.service.AuthService;
import com.neuralgalaxy.stars.user.service.PermissionCheckerService;
import com.neuralgalaxy.stars.user.service.VisitorSerializerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * stars visitor 实现
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@Slf4j
@Configuration
@AutoConfigureBefore(VisitorAutoConfiguration.class)
public class StarsVisitorAutoConfiguration {

    @DubboReference(lazy = true)
    public AuthService authService;

    @Bean
    public VisitorSerializerService visitorSerializerService() {
        log.info("use remove VisitorSerializer");
        return new VisitorSerializerService();
    }

    @Bean
    public PermissionCheckerService permissionCheckerService() {
        log.info("use remove PermissionChecker");
        return new PermissionCheckerService();
    }

    @Bean
    public VisitorProperties config() {
        VisitorProperties config = new VisitorProperties();
        config.setUserModel(UserTokenModel.class);
        log.info("set user token model: {}", config.getUserModel().getName());
        return config;
    }

}
