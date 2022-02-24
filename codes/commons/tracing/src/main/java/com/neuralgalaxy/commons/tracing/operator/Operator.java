package com.neuralgalaxy.commons.tracing.operator;

import java.lang.annotation.*;

/**
 * 日志追踪
 *
 * @author haiker
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operator {
    String value() default "";

    String action() default "";

    /**
     * 动态生成内容，动态内容会根据内容传自动渲染
     * @return T/F
     */
    boolean dynamic() default false;
}
