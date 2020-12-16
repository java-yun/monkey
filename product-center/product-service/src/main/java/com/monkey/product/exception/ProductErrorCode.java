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
    public static final ProductErrorCode PRODUCT_INIT_ERROR = new ProductErrorCode("001", "product init exception");
    public static final ProductErrorCode INDEX_CREATE_ERROR = new ProductErrorCode("002", "index create exception");
    public static final ProductErrorCode PRODUCT_SEARCH_ERROR = new ProductErrorCode("003", "elasticsearch query exception");
    public static final ProductErrorCode BULK_OPERATOR_ERROR = new ProductErrorCode("004", "bulk operator exception");
    public static final ProductErrorCode INDEX_REFRESH_ERROR = new ProductErrorCode("005", "index refresh exception");
    public static final ProductErrorCode PRODUCT_TYPE_ERROR = new ProductErrorCode("006", "product type error");

    private ProductErrorCode(String code, String msg, CodePrefix codePrefix) {
        super(code, msg, codePrefix);
    }

    public ProductErrorCode(String code, String msg) {
        this(code, msg, MonkeyCodePrefix.PRODUCT);
    }
}
