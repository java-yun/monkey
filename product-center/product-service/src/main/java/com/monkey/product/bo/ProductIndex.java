package com.monkey.product.bo;

import com.monkey.product.constants.BusinessConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品 es 索引实体  product_index_v1
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = BusinessConstants.PRODUCT_INDEX_V1, shards = 1, replicas = 1, refreshInterval = "-1")
public class ProductIndex {

    @Field(type = FieldType.Integer, index = false)
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String code;

    @Field(type = FieldType.Byte)
    private Byte type;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Integer)
    private Integer brandId;

    @Field(type = FieldType.Integer)
    private Integer categoryId;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(type = FieldType.Text, index = false)
    private String mainPic;

    @Field(type = FieldType.Text, index = false)
    private String detailPics;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String brief;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String description;

    @Field(type = FieldType.Byte)
    private Byte auditStatus;

    @Field(type = FieldType.Byte)
    private Byte isOnSale;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date onSaleTime;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Date offSaleTime;

    @Field(type = FieldType.Integer)
    private Integer stockNum;
}
