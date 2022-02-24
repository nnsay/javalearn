package com.neuralgalaxy.commons.tracing.operator;

import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 关键操作追踪记录保存服务
 *
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 2020/09/03
 */
public interface OperatorLoggerServer {

    /**
     * 关键操作日志记录保存
     *
     * @param action  用户操作，默认ClassName.method
     * @param message 操作内容描述
     */
    @Async
    void log(String action, String message, Map<String, ?> args);
}
