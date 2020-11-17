package com.monkey.product.service;


import com.monkey.product.entity.Product;

import java.util.List;

/**
 * 商品service 接口
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 16:20
 */
public interface ProductService {

    /**
     * 保存商品
     * @param product 商品实体
     */
    void save(Product product);

    /**
     * 查询上架商品
     * @return List<Product>
     */
    List<Product> getOnSaleProduct();

    /**
     * 数据库是否有商品记录
     * @return true or  false
     */
    boolean hasProductRecord();
}
