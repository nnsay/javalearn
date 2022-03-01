package com.neuralgalaxy.stars.users;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.neuralgalaxy.stars.StarsApplication;
import com.neuralgalaxy.stars.user.model.UserLoginModel;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = StarsApplication.class)
public class UsersTests {

    @Autowired
    private MockMvc mock;

    @Test
    public void testLoginEmail() throws Exception {
        UserLoginModel user = new UserLoginModel();
        user.setUsername("org1@neuralgalaxy.com");
        user.setPasswd("testiest");
        String token = login(user);
        viewMe(token);
    }

    @Test
    public void testLoginByUserName() throws Exception {
        UserLoginModel user = new UserLoginModel();
        user.setUsername("haiker");
        user.setPasswd("testiest");
        String token = login(user);
        viewMe(token);
    }

    public String login(UserLoginModel login) throws Exception {
        return this.mock.perform(post("/user/login")
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .content(JSON.toJSONBytes(login)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

    }

    public void viewMe(String token) throws Exception {
        this.mock.perform(get("/user/me").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
