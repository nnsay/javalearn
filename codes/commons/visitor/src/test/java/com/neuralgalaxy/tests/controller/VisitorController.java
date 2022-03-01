package com.neuralgalaxy.tests.controller;

import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.tests.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class VisitorController {

    @Autowired
    VisitorSerializer serializer;

    @PostMapping("/user/login")
    public String index(@RequestParam("username") String username, @RequestParam("passwd") String passwd) {
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setPasswordd(passwd);
        user.setId(1);
        return serializer.encode(user);
    }

    @GetMapping("/user/must")
    @ResponseStatus
    public int required(@CurrentVisitor Visitor visitor) {
        log.info("user access {}", visitor);
        return HttpStatus.OK.value();
    }

    @GetMapping("/user/require")
    public String canAnonymous(@CurrentVisitor(require = false) UserModel visitor) {
        if (visitor.isAnonymous()) {
            return "anonymous";
        } else {
            return String.valueOf(visitor.getId());
        }
    }

    @GetMapping("/role/user/list")
    @PreAuthorize("@sec.hasAuthority('user:list')")
    public String hasUserRole(@CurrentVisitor Visitor visitor) {
        return "OK";
    }

    @GetMapping("/role/user/set")
    @PreAuthorize("@sec.hasAuthority('user:set')")
    public String hasAuthorityUserSet(@CurrentVisitor Visitor visitor) {
        return "OK";
    }
}
