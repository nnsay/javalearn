package com.neuralgalaxy.commons.stars.user.api;

import com.neuralgalaxy.commons.stars.user.model.UserTokenModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220311
 */
@FeignClient(value = "stars", path = "/internal")
public interface AuthClient {

    /**
     * 判断用户收含有权限
     *
     * @param userId          用户ID
     * @param applicationName 项目名称：point,view, etc...
     * @param authority       权限内容
     * @return HttpStatus = 202 含有
     */
    @GetMapping("/auth/check/{userId}")
    HttpStatus hasAuthority(
            @PathVariable("userId") long userId,
            @RequestParam("applicationName") String applicationName,
            @RequestParam("authority") String authority
    );

    /**
     * 解析用户token
     *
     * @param token jwt token
     * @return 用户信息
     */
    @PostMapping("/auth/token/decode")
    UserTokenModel decodeUserToken(@RequestBody String token);
}
