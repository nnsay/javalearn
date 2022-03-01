package com.neuralgalaxy.stars.users.controllers;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.tracing.operator.Operator;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.stars.user.model.UserModel;
import com.neuralgalaxy.stars.user.service.UserService;
import com.neuralgalaxy.stars.user.model.UserLoginModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220216
 */
@Api(tags = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiResponses(@ApiResponse(responseCode = "10400", description = "参数异常"))
    public String userLogin(@RequestBody UserLoginModel userLoginModel) {
        return userService.login(userLoginModel);
    }

    @GetMapping("/me")
    @ApiOperation("查看自己的信息")
    public UserModel userInfo(@CurrentVisitor @ApiIgnore Visitor visitor) {
        UserModel user = userService.getUser(visitor.getId());
        Asserts.notNull(user, GlobalErrors.BAD_REQUEST);
        return user;
    }

    @PutMapping("/add")
    @ApiOperation("添加用户")
    @Operator(action = "UserAdd", dynamic = true, value = "添加用户")
    @PreAuthorize("@sec.hasAuthority('user:add')")
    public HttpStatus addUser(UserModel userModel) {
        return HttpStatus.OK;
    }
}
