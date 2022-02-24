package com.neuralgalaxy.commons.tracing.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 关键操作追踪配置
 *
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 2020/09/03
 */
@Configuration
public class OperatorAutoConfiguration {

    /**
     * 定义追踪记录方式，默认日志方式，如果自定义了将使用自定义方式
     */
    @Bean
    @ConditionalOnMissingBean
    public OperatorLoggerServer tracingServer() {
        return new DefaultOperatorLoggerServer();
    }

    @Bean
    public OperatorAspect tracingAspect(@Autowired OperatorLoggerServer tracingServer,
                                        @Autowired(required = false) OperatorGlobalAttributes attributes) {
        return new OperatorAspect(tracingServer, attributes);
    }
}
