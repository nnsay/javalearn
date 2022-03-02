package com.neuralgalaxy.commons.visitor.role;

import com.neuralgalaxy.commons.visitor.Visitor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220228
 */
public class PermissionHandler {

    protected String applicationName;
    protected PermissionChecker permissionChecker;

    public PermissionHandler(String applicationName, PermissionChecker permissionChecker) {
        this.applicationName = applicationName;
        this.permissionChecker = permissionChecker;
    }

    /**
     * 检查权限
     *
     * @param authority 权限值
     * @return 是否存在权限
     */
    public boolean hasAuthority(String authority) {
        Visitor visitor = (Visitor) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return permissionChecker.hasAuthority(visitor.getId(), this.applicationName, authority);
    }
}
