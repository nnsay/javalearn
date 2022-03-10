package com.neuralgalaxy.stars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 程序启动入口
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220213
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class StarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarsApplication.class, args);
    }
}
