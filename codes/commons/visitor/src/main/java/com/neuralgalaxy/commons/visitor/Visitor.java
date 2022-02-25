package com.neuralgalaxy.commons.visitor;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 系统访问用户
 *
 * @author haiker
 */
public interface Visitor {
    /**
     * visitor对象存储在request位置
     */
    String REQUEST_ATTRIBUTE_NAME = "__visitor__";

    /**
     * 用户ID
     *
     * @return userId
     */
    long getId();

    /**
     * 是否是匿名用户
     *
     * @return T/F
     */
    @JsonIgnore
    default boolean isAnonymous() {
        return 0 == getId();
    }
}
