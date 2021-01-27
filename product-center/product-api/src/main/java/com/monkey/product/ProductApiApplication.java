package com.monkey.product;

import com.monkey.product.service.ProductInitService;
import com.monkey.product.service.ProductService;
import com.monkey.product.service.impl.ProductElasticsearchService;
import com.monkey.product.thread.ProductMainThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

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
        var countDownLatch = new CountDownLatch(4);
        IntStream.range(0, 4).forEach((i) -> {
            ProductMainThreadPoolManager.getInstance().wrapSubmit(() -> productInitService.init(countDownLatch));
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("get thread execute result exception", e);
        }

    }
}
