package com.monkey.product.service;

import com.monkey.product.bo.ProductSearchRequest;
import com.monkey.product.vo.ProductSearchResult;

/**
 * 商品 搜索 service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 14:35
 */
public interface ProductSearchService {

    /**
     * 查询商品
     * @param productSearchRequest request
     * @return ProductSearchResult
     */
    ProductSearchResult searchProduct(ProductSearchRequest productSearchRequest);
}
