package com.monkey.product.service;


import com.monkey.common.response.Response;
import com.monkey.product.entity.Product;
import com.monkey.product.request.ProductSubmissionRequest;

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

    /**
     * 查询上架商品的总记录数
     * @return totalCount
     */
    long getOnSaleTotalCount();

    /**
     * 按照id 排序批量查询
     * @param cursor 起始id
     * @param size 大小
     * @return List<Product>
     */
    List<Product> getOnSaleProductWithLimit(int cursor, int size);

    /**
     * 商品提报
     * @param request request
     */
    void productSubmission(ProductSubmissionRequest request);
}
