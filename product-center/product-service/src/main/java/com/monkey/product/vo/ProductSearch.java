package com.monkey.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * es 商品查询结果
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/26 14:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearch {

    private Integer id;

    private String code;

    private Byte type;

    private String name;

    private Integer brandId;

    private Integer categoryId;

    private BigDecimal price;

    private String mainPic;

    private String detailPics;

    private String brief;

    private String description;

    private Date onSaleTime;

    private Integer stockNum;
}
