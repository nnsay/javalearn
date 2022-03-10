package com.neuralgalaxy.stars.users.controller;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.tracing.operator.Operator;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.stars.users.model.UserModel;
import com.neuralgalaxy.stars.users.service.UserService;
import com.neuralgalaxy.stars.users.model.UserLoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220216
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String userLogin(@RequestBody UserLoginModel userLoginModel) {
        return userService.login(userLoginModel);
    }

    @GetMapping("/me")
    public UserModel userInfo(@CurrentVisitor Visitor visitor) {
        UserModel user = userService.getUser(visitor.getId());
        Asserts.notNull(user, GlobalErrors.BAD_REQUEST);
        return user;
    }

    @PutMapping("/add")
    @Operator(action = "UserAdd", dynamic = true, value = "添加用户")
    @PreAuthorize("@sec.hasAuthority('user:add')")
    public HttpStatus addUser(UserModel userModel) {
        return HttpStatus.OK;
    }
}
