package com.monkey.product.enums;

/**
 * 审核状态枚举
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/3 16:45
 */
public enum AuditStatusEnum {
    /**
     * 审核状态
     */
    SUBMISSION(Byte.valueOf("1"), "提报"),
    FIRST_TRIAL(Byte.valueOf("2"), "初审通过"),
    REVIEW_TRIAL(Byte.valueOf("3"), "复审通过"),
    ;

    public static AuditStatusEnum fromValue(Byte status) {
        for (AuditStatusEnum statusEnum: AuditStatusEnum.values()) {
            if (statusEnum.getStatus().equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }

    private Byte status;
    private String desc;

    AuditStatusEnum(Byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
