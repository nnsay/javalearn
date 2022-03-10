package com.neuralgalaxy.stars.users.service;

import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220302
 */
@Service
public class  AuthService implements PermissionChecker {

    @Override
    public boolean hasAuthority(long userId, String applicationName, String authority) {
        return false;
    }
}
