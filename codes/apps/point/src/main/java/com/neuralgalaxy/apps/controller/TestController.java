package com.neuralgalaxy.apps.controller;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.stars.auth.model.UserTokenModel;
import com.neuralgalaxy.stars.user.model.UserModel;
import com.neuralgalaxy.stars.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220224
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试使用")
public class TestController {

    @DubboReference
    public UserService userService;


    @ApiOperation("获取用户信息")
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World";
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user/{userId}")
    public UserModel getUser(@PathVariable("userId") long userId) {
        UserModel user = userService.getUser(userId);
        Asserts.notNull(user, GlobalErrors.BAD_REQUEST);
        return user;
    }

    @ApiOperation("添加治疗计划")
    @PostMapping("/plan/add")
    @PreAuthorize("@sec.hasAuthority('plan:add')")
    public HttpStatus addPlan(@CurrentVisitor Visitor visitor) {
        return HttpStatus.OK;
    }

    @ApiOperation("修改治疗计划")
    @PutMapping("/plan/edit")
    @PreAuthorize("@sec.hasAuthority('plan:edit')")
    public HttpStatus editPlan(@CurrentVisitor UserTokenModel user) {
        return HttpStatus.OK;
    }
}
