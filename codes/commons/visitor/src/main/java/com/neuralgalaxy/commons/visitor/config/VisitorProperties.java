package com.neuralgalaxy.commons.visitor.config;

import com.neuralgalaxy.commons.visitor.Visitor;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 &amp; 2019-06-24 15:44
 */
@Data
@ConfigurationProperties("visitor")
public class VisitorProperties  {

    /**
     * visitor cookie 获取名称
     */
    String cookieName = HttpHeaders.AUTHORIZATION;

    /**
     * 实例类名
     */
    Class<? extends Visitor> entityClassName;

    /**
     * 额外配置信息，为以后扩展使用
     */
    Map<String, String> config = new HashMap<>();

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
}
