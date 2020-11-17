package com.monkey.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品数据库实体
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 11:39
 */
@Data
@Entity
@Table(name = "yun_product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String code;

    private Byte type;

    private String name;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "category_id")
    private Integer categoryId;

    private BigDecimal price;

    @Column(name = "main_pic")
    private String mainPic;

    @Column(name = "detail_pics")
    private String detailPics;

    private String brief;

    private String description;

    @Column(name = "audit_status")
    private Byte auditStatus;

    @Column(name = "is_on_sale")
    private Byte isOnSale;

    @Column(name = "on_sale_time")
    private Date onSaleTime;

    @Column(name = "off_sale_time")
    private Date offSaleTime;

    @Column(name = "stock_num")
    private Integer stockNum;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user")
    private String updateUser;
}
