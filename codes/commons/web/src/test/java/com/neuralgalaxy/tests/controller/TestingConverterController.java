package com.neuralgalaxy.tests.controller;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.tests.vo.TestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */
@RestController
public class TestingConverterController {

    @GetMapping("/json")
    public TestEntity index(
            @RequestParam(value = "username", required = false) String name,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "age", required = false) Integer age
    ) {
        Asserts.notEmpty(name, GlobalErrors.BAD_REQUEST);

        TestEntity test = new TestEntity();
        test.setName(name);
        test.setPassword(password);
        test.setAge(age);
        return test;
    }

    @GetMapping("/message")
    public String message() {
        return "ok";
    }
}
