package com.monkey.common.enums;

/**
 * 短信验证码 业务类型
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 11:04
 */
public enum MsgBizTypeEnum {

    /**
     * 短信验证码 业务类型
     */
    CMS_LOGIN("cms_login", "cms-web登陆"),
    ;

    private final String type;
    private final String desc;

    MsgBizTypeEnum(String type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static MsgBizTypeEnum fromValue(String type) {
        for (MsgBizTypeEnum msgTypeEnum : MsgBizTypeEnum.values()) {
            if (msgTypeEnum.getType().equals(type)) {
                return msgTypeEnum;
            }
        }
        return null;
    }

    public static String getDescByName(String type) {
        for(MsgBizTypeEnum msgTypeEnum : MsgBizTypeEnum.values()) {
            if (msgTypeEnum.getType().equals(type)) {
                return msgTypeEnum.getDesc();
            }
        }
        return "";
    }

    public String getType() {
        return type;
    }


    public String getDesc() {
        return desc;
    }
}
