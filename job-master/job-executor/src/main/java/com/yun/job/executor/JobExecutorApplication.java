package com.yun.job.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication(scanBasePackages = {"com.yun.job.executor", "com.monkey"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.monkey"})
public class JobExecutorApplication {

	public static void main(String[] args) {
        SpringApplication.run(JobExecutorApplication.class, args);
	}

}