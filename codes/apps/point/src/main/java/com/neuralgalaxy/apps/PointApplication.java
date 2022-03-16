package com.neuralgalaxy.apps;

import com.neuralgalaxy.apps.bean.OSSToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PointApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PointApplication.class, args);

        // list bean
        // String[] names = run.getBeanDefinitionNames();
        // for (String name : names) {
        //     System.out.println(name);
        // }
        OSSToken ossToken = run.getBean("ossToken", OSSToken.class);
        System.out.println("AK:" + ossToken.getAccessKey());
    }
}
