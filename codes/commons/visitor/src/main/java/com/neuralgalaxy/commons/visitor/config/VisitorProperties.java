package com.neuralgalaxy.commons.visitor.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.visitor.Visitor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 &amp; 2019-06-24 15:44
 */
@Slf4j
@Data
@ConfigurationProperties("visitor")
public class VisitorProperties implements InitializingBean {

    /**
     * 实例类名
     */
    Class<? extends Visitor> userModel;

    /**
     * 权限对应的应用名称，相同的权限，对于不同系统也是有不同的情况，使用此字段决定系统区分
     */
    String applicationName;

    /**
     * JWT配置信息
     */
    Jwt jwt;

    @Data
    public static class Jwt {
        /**
         * jwt秘钥
         */
        private String secret;

        /**
         * 过期时间，单位秒, 默认：-1 不过期
         */
        private int expireSecond = -1;
    }

    @Autowired
    @JsonIgnore
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(this.getApplicationName())) {
            this.applicationName = environment.resolvePlaceholders("${spring.application.name:}");
        }
        Assert.hasText(this.applicationName,"[Assertion failed] visitor.applicationName is empty");
        log.info("visitor application name is: {}", applicationName);
    }
}
