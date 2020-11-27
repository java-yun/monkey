package com.monkey.product.vo;

import java.util.List;

/**
 * 商品查询结果
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 14:25
 */
public record ProductSearchResult(List<ProductSearch>productList) {
}
