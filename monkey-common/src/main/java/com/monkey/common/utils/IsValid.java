package com.monkey.common.utils;

/**
 * 是否有效
 * @author jiangyun
 */
public enum IsValid {

    /**
     * 计算机中 0代表假  1代表真
     */
    invalid("0","否")
    ,valid("1","是")

    ;


    private String value;
    private String desc;

    IsValid(String value,String desc){
        this.value = value;
        this.desc = desc;
    }

    public static IsValid fromValue(String value) {
        for (IsValid isValid : IsValid.values()) {
            if (isValid.getValue().equals(value)) {
                return isValid;
            }
        }
        return null;
    }

    public static String getDescByValue(String value) {
        for(IsValid isValid : IsValid.values()) {
            if (isValid.getValue().equals(value)) {
                return isValid.getDesc();
            }
        }
        return "";
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
