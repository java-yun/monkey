package com.monkey.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品查询结果
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 14:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchResult {

    private List<ProductSearch>productList;

    private String afterKey;
}
