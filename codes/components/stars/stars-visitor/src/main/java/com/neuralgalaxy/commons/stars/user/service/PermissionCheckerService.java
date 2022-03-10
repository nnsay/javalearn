package com.neuralgalaxy.commons.stars.user.service;

import com.neuralgalaxy.commons.stars.user.api.AuthClient;
import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import org.springframework.http.HttpStatus;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
public class PermissionCheckerService implements PermissionChecker {

    AuthClient authClient;

    public PermissionCheckerService(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public boolean hasAuthority(long userId, String applicationName, String authority) {
        HttpStatus status = authClient.hasAuthority(userId, applicationName, authority);
        return HttpStatus.NO_CONTENT.equals(status);
    }

}
