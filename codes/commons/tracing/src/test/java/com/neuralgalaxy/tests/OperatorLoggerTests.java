package com.neuralgalaxy.tests;

import com.neuralgalaxy.tests.server.OperatorTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220221
 */
@SpringBootTest
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
}
