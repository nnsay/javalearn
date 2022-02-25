package com.neuralgalaxy.tests;

import com.neuralgalaxy.tests.server.OperatorTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220221
 */
@SpringBootTest
@SpringBootApplication
public class OperatorLoggerTests {

    @Autowired
    OperatorTestService service;

    @Test
    public void addSome() {
        service.addSome();
    }

    @Test
    public void delSome() {
        service.delete(1, 2);
    }


    public static void main(String[] args) {
        SpringApplication.run(OperatorLoggerTests.class, args);
    }
}
