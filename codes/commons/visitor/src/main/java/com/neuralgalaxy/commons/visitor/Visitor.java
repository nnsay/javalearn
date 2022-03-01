package com.neuralgalaxy.commons.visitor;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 系统访问用户
 *
 * @author haiker
 */
public interface Visitor {
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
