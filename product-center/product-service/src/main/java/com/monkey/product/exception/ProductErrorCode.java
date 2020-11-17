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

    private ProductErrorCode(String code, String msg, CodePrefix codePrefix) {
        super(code, msg, codePrefix);
    }

    public ProductErrorCode(String code, String msg) {
        this(code, msg, MonkeyCodePrefix.PRODUCT);
    }
}
