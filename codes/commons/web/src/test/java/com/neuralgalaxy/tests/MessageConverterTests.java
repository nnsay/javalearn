package com.neuralgalaxy.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageConverterTests {

    @Autowired
    private MockMvc mock;

    @Test
    public void testException() throws Exception {
        this.mock.perform(get("/json"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testString() throws Exception {
        this.mock.perform(get("/message"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

    @Test
    public void testJSON() throws Exception {
        this.mock.perform(get("/json")
                        .param("username", "test")
                        .param("password", "test")
                        .param("age", "18")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test")) //正常字段
                .andExpect(jsonPath("$.password").doesNotExist()) //不允许返回字段
                .andExpect(jsonPath("$.nianling").value(18)) //修改字段
                .andExpect(jsonPath("$.members").isArray());
        ;
    }
}
