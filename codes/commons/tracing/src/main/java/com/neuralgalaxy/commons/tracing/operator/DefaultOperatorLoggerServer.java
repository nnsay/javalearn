package com.neuralgalaxy.commons.tracing.operator;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 日志方式记录关键操作日志
 *
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 2020/09/03
 */
@Slf4j
public class DefaultOperatorLoggerServer implements OperatorLoggerServer {

    @Override
    public void log(String action, String message, Map<String, ?> args) {
        log.info("[{}] {} ", action, message);
    }
}
