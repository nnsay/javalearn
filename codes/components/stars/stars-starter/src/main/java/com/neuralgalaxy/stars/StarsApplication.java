package com.neuralgalaxy.stars;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.*;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220213
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableOpenApi
public class StarsApplication {

    private Predicate<String> starsApi() {
        return ant("/user/**")
                .or(ant("/auth/**"));
    }
    private ApiInfo apiInfo(String applicationName) {
        return new ApiInfoBuilder()
                .title(applicationName).build();
    }

    @Bean
    public Docket userApi(@Value("${spring.application.name}") String name) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(name))
                .select().paths(starsApi())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(StarsApplication.class, args);
    }
}
