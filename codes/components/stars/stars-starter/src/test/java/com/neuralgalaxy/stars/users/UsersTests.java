package com.neuralgalaxy.stars.users;

import com.alibaba.fastjson.JSON;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.web.WebAutoConfiguration;
import com.neuralgalaxy.stars.StarsApplication;
import com.neuralgalaxy.stars.users.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
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
        UserLoginVo user = new UserLoginVo();
        user.setUsername("org1@neuralgalaxy.com");
        user.setPasswd("testiest");

        String token = this.mock.perform(post("/users/login").content(JSON.toJSONBytes(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        viewMe(token);
    }

    @Test
    public void testLoginByUserName() throws Exception {
        UserLoginVo user = new UserLoginVo();
        user.setUsername("haiker");
        user.setPasswd("testiest");
        String token = this.mock.perform(post("/users/login").content(JSON.toJSONBytes(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        viewMe(token);
    }

    public void viewMe(String token) throws Exception {
        this.mock.perform(get("/users/me").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
