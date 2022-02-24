package com.neuralgalaxy.stars.users.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author haiker
 * @version 20220216
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "users")
public class UserConfiguration {

    /**
     * //demo 登录必须使用orgName 和 username 一起登录,
     */
    private boolean loginMustWithOrgName = false;
}
