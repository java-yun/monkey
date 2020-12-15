package com.monkey.product.enums;

/**
 * 商品类型 枚举
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 17:31
 */

public enum ProductTypeEnum {

    /**
     * 商品类型
     */
    ORDINARY(Byte.valueOf("1"), "普通商品"),
    ACTIVITY(Byte.valueOf("2"), "活动商品"),
    ;

    public static ProductTypeEnum fromValue(Byte status) {
        for (ProductTypeEnum typeEnum: ProductTypeEnum.values()) {
            if (typeEnum.getType().equals(status)) {
                return typeEnum;
            }
        }
        return null;
    }

    private Byte type;
    private String desc;

    ProductTypeEnum(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
