package com.neuralgalaxy.commons.visitor;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 访问用户自动注入
 *
 * @author haiker
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentVisitor {

    /**
     * @return 是否是必须
     * @see #require()
     */
    @AliasFor("require")
    boolean value() default true;

    /**
     * 是否必须，如果必须且客户端并未上传则直接返回登录超时
     *
     * @return 用户信息是否必须
     */
    @AliasFor("value")
    boolean require() default true;
}
