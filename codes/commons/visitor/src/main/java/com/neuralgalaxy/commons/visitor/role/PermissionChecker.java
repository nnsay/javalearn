package com.neuralgalaxy.commons.visitor.role;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220228
 */
public interface PermissionChecker {

    /**
     * 检查系统
     *
     * @param applicationName 系统名称
     * @param authority       权限名称
     * @return 是否包含次权限
     */
    boolean hasAuthority(long id, String applicationName, String authority);
}
