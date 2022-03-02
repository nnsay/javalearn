package com.neuralgalaxy.stars.user.service;

import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import com.neuralgalaxy.stars.StarsVisitorAutoConfiguration;
import com.neuralgalaxy.stars.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
public class PermissionCheckerService implements PermissionChecker, ApplicationRunner {

    AuthService authService;

    @Autowired
    ApplicationContext context;

    @Override
    public boolean hasAuthority(long id, String applicationName, String authority) {
        return authService.hasAuthority(id, applicationName, authority);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authService =  context.getBean(StarsVisitorAutoConfiguration.class).authService;
    }
}
