package com.monkey.common.constants;

/**
 * 短信验证码 相关常量
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 14:44
 */
public final class MsgCodeConstants {
    public static final long EXPIRE_TIME = 5 * 60;

    public static final int SEND_TIMES_DAY = 24;
    public static final int SEND_TIMES_HOUR = 6;
    public static final long EXPIRE_TIME_DAY_LIMIT = 24 * 60 * 60;
    public static final long EXPIRE_TIME_HOUR_LIMIT = 60 * 60;
    public static final long EXPIRE_TIME_MINUTE_LIMIT = 60;

}
