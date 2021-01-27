package com.monkey.product.service;

import java.util.concurrent.CountDownLatch;

/**
 * 商品初始化 service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 17:54
 */
@FunctionalInterface
public interface ProductInitService {

    /**
     * 初始化商品
     * @param countDownLatch countDownLatch
     */
    void init(CountDownLatch countDownLatch);
}
