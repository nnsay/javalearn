package com.neuralgalaxy.stars.auth.service;

import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
@Service
public class RoleServiceImpl implements PermissionChecker {

    @Override
    public boolean hasAuthority(long id, String applicationName, String authority) {
        return false;
    }

}
