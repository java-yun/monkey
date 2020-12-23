package com.monkey.common.constants;

/**
 * 阿里云 常量
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 11:20
 */
public final class AliyunConstants {

    /**
     * 短信配置
     */
    public final static String MSG_ACCESS_KEY_ID_REDIS_KEY = "monkey:msgCode:aliyun:accessKeyId";
    public final static String MSG_ACCESS_KEY_SECRET_REDIS_KEY = "monkey:msgCode:aliyun:accessKeySecret";
    public final static String MSG_SIGNATURE_REDIS_KEY = "monkey:msgCode:aliyun:signature";
    public final static String MSG_TEMPLATE_REDIS_KEY = "monkey:msgCode:aliyun:template";

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    public static final String PRODUCT = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    public static final String DOMAIN = "dysmsapi.aliyuncs.com";

    public static final String SMS_CODE_EXPIRE = "500";

    public static final String MSG_SEND_SUC_CODE = "OK";
}
