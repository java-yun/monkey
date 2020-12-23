package com.monkey.common.enums;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 14:06
 */
public enum MsgCodeRedisKeyEnum {

    /**
     * 短信验证码 redis key
     */
    MESSAGE_CODE_TIME_M("monkey:message_code:m:","短信验证码--分钟次数"),
    MESSAGE_CODE_TIME_H("monkey:message_code:h:","短信验证码--小时次数"),
    MESSAGE_CODE_TIME_D("monkey:message_code:d:","短信验证码--天次数"),
    MSG_CODE("msgCode:", "短信验证码部分前缀"),
    ;

    private final String key;
    private final String desc;

    MsgCodeRedisKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
