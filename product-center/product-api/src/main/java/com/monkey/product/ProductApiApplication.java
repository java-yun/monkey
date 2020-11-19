package com.monkey.product;

import com.monkey.product.bo.ProductIndex;
import com.monkey.product.repository.ElasticsearchOperateRepository;
import com.monkey.product.service.ProductInitService;
import com.monkey.product.service.ProductService;
import com.monkey.product.thread.ProductThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 商品服务
 * @author jiangyun
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProductApiApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductInitService productInitService;

    @Autowired
    private ElasticsearchOperateRepository elasticsearchOperateRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        this.initProduct();
        this.syncProductToEs();
    }

    private void syncProductToEs() {
        //查询上架商品
        var products = this.productService.getOnSaleProduct();
        //创建索引
        this.elasticsearchOperateRepository.createIndex(ProductIndex.class);
    }

    private void initProduct() {
        if (!this.productService.hasProductRecord()) {
            ProductThreadPoolManager.getInstance().execute(productInitService::init);
            ProductThreadPoolManager.getInstance().execute(productInitService::init);
            ProductThreadPoolManager.getInstance().execute(productInitService::init);
            ProductThreadPoolManager.getInstance().execute(productInitService::init);
        }
    }
}
