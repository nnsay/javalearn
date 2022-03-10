package com.neuralgalaxy.stars.internal.auth.controller;

import com.neuralgalaxy.commons.stars.user.model.UserTokenModel;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.stars.users.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220311
 */
@Slf4j
@RestController
@RequestMapping("/internal")
public class AuthInternalController {

    @Autowired
    AuthService authService;

    @Autowired
    VisitorSerializer visitorSerializer;

    /**
     * 判断用户收含有权限
     *
     * @param userId          用户ID
     * @param applicationName 项目名称：point,view, etc...
     * @param authority       权限内容
     * @return HttpStatus = 202 含有
     */
    @GetMapping("/auth/check/{userId}")
    public HttpStatus hasAuthority(
            @PathVariable("userId") long userId,
            @RequestParam("applicationName") String applicationName,
            @RequestParam("authority") String authority
    ) {
        boolean has = authService.hasAuthority(userId, applicationName, authority);
        log.debug("[{}] {} has {} = {}", applicationName, userId, authority, has);
        return HttpStatus.NO_CONTENT;
    }

    /**
     * 解析用户token
     * @param token
     * @return
     */
    @PostMapping("/auth/token/decode")
    public UserTokenModel decodeUserToken(@RequestBody String token) {
        return (UserTokenModel) visitorSerializer.decode(token);
    }
}
