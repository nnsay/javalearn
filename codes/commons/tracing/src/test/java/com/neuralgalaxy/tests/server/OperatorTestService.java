package com.neuralgalaxy.tests.server;

import com.neuralgalaxy.commons.tracing.operator.Operator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
@Slf4j
@Service
public class OperatorTestService {

    @Operator(value = "add some", action = "AddName")
    public String addName() {
        log.info("user add some one!!");
        return "OK";
    }

    @Operator("add some")
    public String addSome() {
        log.info("user add some one!!");
        return "OK";
    }

    @Operator(value = "user delete {$.args[0]} 删除 {$.args[1]}", dynamic = true)
    public String delete(long userId, long id) {
        log.info("user add some one!!");
        return "OK";
    }
}
