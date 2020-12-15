package com.monkey.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品提报 request
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/11 17:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSubmissionRequest {

    private Byte type;

    private String name;

    private Integer brandId;

    private Integer categoryId;

    private BigDecimal price;

    private String mainPic;

    private String detailPics;

    private String brief;

    private String description;

    private Integer stockNum;

    private String createUser;

    private String updateUser;
}
