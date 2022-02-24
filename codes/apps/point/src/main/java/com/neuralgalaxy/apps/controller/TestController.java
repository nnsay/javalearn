package com.neuralgalaxy.apps.controller;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.stars.user.dto.UserDo;
import com.neuralgalaxy.stars.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220224
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试使用")
public class TestController {

    @DubboReference
    UserService userService;

    @ApiOperation("获取用户信息")
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World";
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user/{userId}")
    public UserDo getUser(@PathVariable("userId") long userId) {
        UserDo user = userService.getUser(userId);
        Asserts.notNull(user, GlobalErrors.BAD_REQUEST);
        return user;
    }

}
