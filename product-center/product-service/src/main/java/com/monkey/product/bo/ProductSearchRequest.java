package com.monkey.product.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品查询  实体   es
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 14:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchRequest {

    private Integer id;

    private String code;

    private Byte type;

    private Integer brandId;

    private Integer categoryId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Date onSaleTime;

    private String searchKeyword;

    private String orderByType = "ASC";

    private String orderByColumn;

    /**
     * 解决 es 深度分页问题
     */
    private String afterKey;

}
