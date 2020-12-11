package com.monkey.product.exception;

import com.monkey.common.code.BizCode;
import com.monkey.common.code.CodePrefix;
import com.monkey.common.code.MonkeyCodePrefix;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 16:57
 */
public class ProductErrorCode extends BizCode {

    public static final ProductErrorCode TEST = new ProductErrorCode("000", "test");
    public static final ProductErrorCode PRODUCT_INIT_ERROR = new ProductErrorCode("001", "商品初始化异常");
    public static final ProductErrorCode INDEX_CREATE_ERROR = new ProductErrorCode("002", "es索引创建异常");
    public static final ProductErrorCode PRODUCT_SEARCH_ERROR = new ProductErrorCode("003", "es查询异常");
    public static final ProductErrorCode BULK_OPERATOR_ERROR = new ProductErrorCode("004", "es批量操作异常");
    public static final ProductErrorCode INDEX_REFRESH_ERROR = new ProductErrorCode("005", "索引刷新异常");

    private ProductErrorCode(String code, String msg, CodePrefix codePrefix) {
        super(code, msg, codePrefix);
    }

    public ProductErrorCode(String code, String msg) {
        this(code, msg, MonkeyCodePrefix.PRODUCT);
    }
}
