package com.neuralgalaxy.tests;

import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.visitor.CurrentVisitor;
import com.neuralgalaxy.commons.visitor.Visitor;
import com.neuralgalaxy.commons.visitor.config.VisitorProperties;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.commons.visitor.role.PermissionChecker;
import com.neuralgalaxy.tests.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest(args = {
        "--spring.application.name=testiest",
        "--visitor.application-name=testiest2",
        "--visitor.user-model=com.neuralgalaxy.tests.model.UserModel",
        "--visitor.jwt.secret=testsecret",
        "--visitor.jwt.expire-second=2",
        "--spring.cloud.nacos.server-addr=nacos.manager.svc.cluster.local:8848",
}, classes = TestingBootApplication.class)
@AutoConfigureMockMvc
public class VisitorTests {

    @MockBean
    PermissionChecker permissionChecker;

    @Autowired
    VisitorProperties config;

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

    public String token() throws Exception {
        String token = this.mock.perform(post("/user/login")
                        .param("username", "test")
                        .param("passwd", "test"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        log.info("token is: {}", token);
        return token;
    }

    @Test
    public void testRequired() throws Exception {
        String token = this.token();
        this.mock.perform(get("/user/require")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testSessionTimeout() throws Exception {
        String token = token();
        int timeout = config.getJwt().getExpireSecond() * 2;
        Thread.sleep(TimeUnit.SECONDS.toMillis(timeout));
        this.mock.perform(get("/user/require").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value(GlobalErrors.SESSION_TIMEOUT.getCode()));
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
                .andExpect(jsonPath("$.message").value(GlobalErrors.UNAUTHORIZED.getMessageZh()));

        this.mock.perform(get("/user/must").header(HttpHeaders.ACCEPT_LANGUAGE, Locale.US.toLanguageTag()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(GlobalErrors.UNAUTHORIZED.getMessageUs()));
    }

}
