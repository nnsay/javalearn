package com.neuralgalaxy.stars.auth.service;

import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import com.neuralgalaxy.stars.auth.model.UserTokenModel;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220302
 */
@DubboService
public class AuthServiceImpl implements AuthService, PermissionChecker {

    @Override
    public UserTokenModel decodeToken(String token) {
        UserTokenModel user = new UserTokenModel();
        user.setId(1024);
        user.setOrgId(10);
        return user;
    }

    @Override
    public boolean hasAuthority(long id, String applicationName, String authority) {
        return false;
    }
}
