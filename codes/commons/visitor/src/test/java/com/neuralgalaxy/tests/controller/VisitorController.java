package com.neuralgalaxy.tests.controller;

import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.VisitorSerializer;
import com.neuralgalaxy.tests.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */
@RestController
public class VisitorController {

    @Autowired
    VisitorSerializer serializer;

    @PostMapping("/user/login")
    public String index(@RequestParam("username") String username, @RequestParam("passwd") String passwd) {
        UserVo user = new UserVo();
        user.setUsername(username);
        user.setPassword(passwd);
        user.setId(username.hashCode());
        return serializer.encode(user);
    }

    @GetMapping("/user/must")
    @ResponseStatus
    public int visitor(@CurrentVisitor Visitor visitor) {
        return HttpStatus.OK.value();
    }

    @GetMapping("/user/require")
    public String visistor(@CurrentVisitor(require = false) Visitor visitor) {
        if (visitor.isAnonymous()) {
            return "anonymous";
        } else {
            return String.valueOf(visitor.getId());
        }
    }
}
