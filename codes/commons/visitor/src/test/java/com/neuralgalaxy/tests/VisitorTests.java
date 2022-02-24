package com.neuralgalaxy.tests;

import com.neuralgalaxy.commons.asserts.GlobalErrors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestingBootApplication.class)
@AutoConfigureMockMvc
public class VisitorTests {

    @Autowired
    private MockMvc mock;

    @Test
    public void testVisitorUnauthorized() throws Exception {
        this.mock.perform(get("/user/must"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAnonymous() throws Exception {
        this.mock.perform(get("/user/require"))
                .andExpect(status().isOk())
                .andExpect(content().string("anonymous"));
    }

    @Test
    public void testRequired() throws Exception {
        String token = this.mock.perform(get("/user/login")
                        .param("username", "test")
                        .param("passwd", "test"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        this.mock.perform(get("/user/require")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf("test".hashCode())));
    }

    @Test
    public void testSessionTimeout() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJwYXNzd29yZCI6InRlc3QiLCJpc3MiOiJ0ZXN0IiwiYW5vbnltb3VzIjpmYWxzZSwiZXhwIjoxNjQ1NDI0MjUxLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoidGVzdCJ9." +
                "ZSfmTiKwKuIFDTre-V761QnFskehKkADBm-QiFtI-UM";

        this.mock.perform(get("/user/require").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(GlobalErrors.SESSION_TIMEOUT.getCode()));
    }

    @Test
    public void testBadHeader() throws Exception {
        String token = "badheader";
        this.mock.perform(get("/user/require").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getMessageLocals() throws Exception {
        this.mock.perform(get("/user/must").header(HttpHeaders.ACCEPT_LANGUAGE, Locale.CHINESE.toLanguageTag()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("未登录！"));

        this.mock.perform(get("/user/must").header(HttpHeaders.ACCEPT_LANGUAGE, Locale.US.toLanguageTag()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Unauthorized"));
    }

}
