package com.neuralgalaxy.stars;

import com.neuralgalaxy.commons.visitor.VisitorAutoConfiguration;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import com.neuralgalaxy.stars.users.model.UserTokenModel;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * stars visitor 实现
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@Configuration
@AutoConfigureBefore(VisitorAutoConfiguration.class)
public class StarsVisitorAutoConfiguration {

    @Bean
    public VisitorProperties config() {
        VisitorProperties config = new VisitorProperties();
        config.setUserModel(UserTokenModel.class);
        return config;
    }

}
