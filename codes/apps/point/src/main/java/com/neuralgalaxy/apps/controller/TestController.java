package com.neuralgalaxy.apps.controller;

import com.neuralgalaxy.commons.stars.user.model.UserTokenModel;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220224
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World";
    }

    @PostMapping("/plan/add")
    @PreAuthorize("@sec.hasAuthority('plan:add')")
    public HttpStatus addPlan(@CurrentVisitor Visitor visitor) {
        return HttpStatus.OK;
    }

    @PutMapping("/plan/edit")
    @PreAuthorize("@sec.hasAuthority('plan:edit')")
    public HttpStatus editPlan(@CurrentVisitor UserTokenModel user) {
        return HttpStatus.OK;
    }
}
