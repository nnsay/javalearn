package com.neuralgalaxy.tests.controller;


import com.neuralgalaxy.commons.asserts.AssertException;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */
@RestController
@RequestMapping("/exception")
public class TestingOverallExceptionResolverController {

    @GetMapping("/defined")
    public String defined() throws Exception {
        throw GlobalErrors.BAD_GATEWAY;
    }

    final static AssertException USER_DEFINED = new AssertException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "20001", "用户错误", "exception user customer defined");

    @GetMapping("/defined-user")
    public String userDefined() throws Exception {
        throw USER_DEFINED;
    }

    @GetMapping("/not-defined")
    public String notDefined() throws Exception {
        throw new IndexOutOfBoundsException();
    }
}
