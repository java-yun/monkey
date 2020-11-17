package com.monkey.product.exception;

import com.monkey.common.exception.SystemException;

/**
 * 商品模块 业务异常
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 16:55
 */
public class ProductException extends SystemException {

    public ProductException() {
        super();
    }

    public ProductException(String code, String msg) {
        super(code, msg);
    }

    public static ProductException throwException(String code, String msg) {
        return new ProductException(code, msg);
    }

    public static ProductException throwException(ProductErrorCode errorCode) {
        return new ProductException(errorCode.code(), errorCode.msg());
    }
}
