package com.neuralgalaxy.tests;

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
@AutoConfigureMockMvc
public class ExceptionResolverTests {

    @Autowired
    private MockMvc mock;

    @Test
    public void testDefined() throws Exception {
        this.mock.perform(get("/exception/defined"))
                .andExpect(status().isBadGateway());
    }

    @Test
    public void testUserDefined() throws Exception {
        this.mock.perform(get("/exception/defined-user"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("20001"));
    }
}
