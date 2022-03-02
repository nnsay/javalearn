package com.neuralgalaxy.tests;

import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(args = {
        "--visitor.application-name=testiest2",
        "--visitor.user-model=com.neuralgalaxy.tests.model.UserModel",
        "--visitor.jwt.secret=testsecret",
        "--visitor.jwt.expire-second=2",
        "--spring.cloud.nacos.server-addr=mse-9d50eb22-p.nacos-ans.mse.aliyuncs.com:8848",
}, classes = TestingBootApplication.class)
@AutoConfigureMockMvc
public class VisitorAuthorityTests {
    @MockBean
    PermissionChecker permissionChecker;

    @Autowired
    private MockMvc mock;

    @BeforeEach
    public void setPermissionChecker() {
        given(this.permissionChecker.hasAuthority(1, "testiest2", "user:list")).willReturn(true);
        given(this.permissionChecker.hasAuthority(1, "testiest2", "user:set")).willReturn(false);
    }

    public String login(String name) throws Exception {
        String token = this.mock.perform(post("/user/login")
                        .param("username", name)
                        .param("passwd", "testiest"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        log.info("token is: {}", token);
        return token;
    }

    protected void checkStatus(String token, String uri, int status) throws Exception {
        this.mock.perform(get(uri).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().is(status));
    }

    @Test
    public void testAuthority() throws Exception {
        String token = login("testiest");
        checkStatus(token, "/role/user/list", HttpStatus.OK.value());
        checkStatus(token, "/role/user/set", HttpStatus.FORBIDDEN.value());
    }
}
