package com.monkey.product.utils;

import com.monkey.product.enums.ProductTypeEnum;
import com.monkey.product.exception.ProductErrorCode;
import com.monkey.product.exception.ProductException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 商品模块参数校验
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/14 10:14
 */
@Slf4j
public class ProductParamCheckUtils {

    /**
     * 商品类型
     */
    private final Byte productType;
    private final boolean isCheckProductType;



    public static class Builder {
        private Byte productType;
        private boolean isCheckProductType;

        public Builder productType(Byte productType) {
            this.productType = productType;
            this.isCheckProductType = true;
            return this;
        }

        public ProductParamCheckUtils build() {
            return new ProductParamCheckUtils(this);
        }
    }

    private ProductParamCheckUtils(Builder builder) {
        this.isCheckProductType = builder.isCheckProductType;
        this.productType = builder.productType;
    }

    public void checkParam() {
        if (isCheckProductType && Objects.isNull(ProductTypeEnum.fromValue(productType))) {
            log.error("product type is null or not exists");
            throw ProductException.throwException(ProductErrorCode.PRODUCT_TYPE_ERROR);
        }
    }
}
