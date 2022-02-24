package com.neuralgalaxy.commons.mysql;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({MybatisAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
public class MybatisHandlerAutoConfiguration {

    /**
     * 加入管理
     * @return mybatis主机
     */
    @Bean
    public CommonsMetaObjectHandler commonsMetaObjectHandler(){
        return new CommonsMetaObjectHandler();
    }
}
