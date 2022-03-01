package com.neuralgalaxy.commons.visitor.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * 添加自定义配置内容
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
public interface WebSecurityCustomizer {

    /**
     * 添加自定义 {@link HttpSecurity} 配置
     *
     * @param http http
     */
    default void customizer(HttpSecurity http) {

    }

    /**
     * 添加自定义关于 {@link  WebSecurity} 配置
     *
     * @param web web
     */
    default void customizer(WebSecurity web) {

    }
}
