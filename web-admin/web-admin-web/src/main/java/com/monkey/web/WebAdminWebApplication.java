package com.monkey.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiangyun
 */
@SpringBootApplication(scanBasePackages = {"com.monkey"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.monkey"})
public class WebAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminWebApplication.class, args);
    }

}
