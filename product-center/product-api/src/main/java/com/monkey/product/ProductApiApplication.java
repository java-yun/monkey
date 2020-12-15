package com.monkey.product;

import com.monkey.product.service.ProductInitService;
import com.monkey.product.service.ProductService;
import com.monkey.product.service.impl.ProductElasticsearchService;
import com.monkey.product.thread.ProductThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.ExecutionException;

/**
 * 商品服务
 * @author jiangyun
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.monkey"})
@EnableEurekaClient
@EnableFeignClients
@ServletComponentScan
public class ProductApiApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductInitService productInitService;

    @Autowired
    private ProductElasticsearchService productElasticsearchService;

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (!this.productService.hasProductRecord()) {
            this.initProduct();
            this.syncProductToEs();
        }
    }

    private void syncProductToEs() {
        productElasticsearchService.execute();
    }

    private void initProduct() {
        var future1 = ProductThreadPoolManager.getInstance().wrapSubmit(productInitService::init);
        var future2 = ProductThreadPoolManager.getInstance().wrapSubmit(productInitService::init);
        var future3 = ProductThreadPoolManager.getInstance().wrapSubmit(productInitService::init);
        var future4 = ProductThreadPoolManager.getInstance().wrapSubmit(productInitService::init);
        try {
            //父线程等待子线程的执行结果，再执行后续代码
            future1.get();
            future2.get();
            future3.get();
            future4.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("get thread execute result exception", e);
        }

    }
}
