package com.monkey.product.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品 es 索引实体  product_index_v1
 * refreshInterval 设置成-1的原因  大数据量插入时，不自动刷新可以提高插入效率
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/18 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "will_be_override", shards = 1, replicas = 0, refreshInterval = "-1")
public class ProductIndex {

    @Field(type = FieldType.Integer)
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

    @Field(type = FieldType.Long)
    private Date onSaleTime;

    @Field(type = FieldType.Long)
    private Date offSaleTime;

    @Field(type = FieldType.Integer)
    private Integer stockNum;
}
