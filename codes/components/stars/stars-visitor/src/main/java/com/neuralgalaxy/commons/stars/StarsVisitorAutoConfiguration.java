package com.neuralgalaxy.commons.stars;

import com.neuralgalaxy.commons.stars.user.api.AuthClient;
import com.neuralgalaxy.commons.stars.user.model.UserTokenModel;
import com.neuralgalaxy.commons.stars.user.service.PermissionCheckerService;
import com.neuralgalaxy.commons.stars.user.service.VisitorSerializerService;
import com.neuralgalaxy.commons.visitor.VisitorAutoConfiguration;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.EnableFeignClients;
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

    @Configuration
    @ConditionalOnExpression("!environment.containsProperty('visitor.jwt.secret')")
    @EnableFeignClients(clients = AuthClient.class)
    public class RemoteVisitorConfiguration {

        @Bean
        public VisitorSerializerService visitorSerializerService(AuthClient authClient) {
            log.info("use remove VisitorSerializer");
            return new VisitorSerializerService(authClient);
        }

        @Bean
        public PermissionCheckerService permissionCheckerService(AuthClient authClient) {
            log.info("use remote PermissionChecker");
            return new PermissionCheckerService(authClient);
        }
    }

    @Bean
    public VisitorProperties config() {
        VisitorProperties config = new VisitorProperties();
        config.setUserModel(UserTokenModel.class);
        log.info("set user token model: {}", config.getUserModel().getName());
        return config;
    }

}
