package com.monkey.product.constants;

import java.util.List;

/**
 * 商品业务常量
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/4 9:54
 */
public final class BusinessConstants {

    public static final String START_PRODUCT_CODE = "10100000";

    public static final String DEFAULT_USER = "system";

    public static final List<String> PRODUCT_INDEXES = List.of("product_index_v1", "product_index_v2");

    public static final String PRODUCT_INDEX_ALIAS = "product_index_alias";

    /**
     * 商品 es bulk size
     */
    public static final int PRODUCT_BULK_SIZE = 4000;

    /**
     * 商品 批量查询 大小 db
     */
    public static final int PRODUCT_BATCH_QUERY_SIZE = 10000;

    /**
     * 商品 批量插入 大小 db
     */
    public static final int PRODUCT_BATCH_INSERT_SIZE = 1000;

    /**
     * es 查询 size
     */
    public static final int PRODUCT_ES_QUERY_SIZE = 100;

    public static final int SEARCH_KEYWORD_NOT_PARTICIPLE_MAX_LENGTH = 3;
}
