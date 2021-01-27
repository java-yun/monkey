package com.monkey.common.code;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 16:04
 */
public class BizCode implements Code {

    public static final BizCode SUCCESS;

    public static final BizCode ERROR;
    public static final BizCode RPC_TIMEOUT;
    public static final BizCode RESOURCE_EXISTS;
    public static final BizCode NOT_FOUND;
    public static final BizCode THREAD_POOL_PARAM_ERROR;

    public static final BizCode DATA_SYNC_ERROR;
    public static final BizCode DATA_INSERT_ERROR;
    public static final BizCode INDEX_CREATE_ERROR;
    public static final BizCode DUPLICATE_OPERATION;
    public static final BizCode MSG_SEND_ERROR;
    public static final BizCode ILLEGAL_PHONE_NUM;
    public static final BizCode MSG_OVER_DAY_TIMES;
    public static final BizCode MSG_OVER_HOUR_TIMES;
    public static final BizCode MSG_OVER_MINUTE_TIMES;
    public static final BizCode MSG_CODE_EXPIRED;
    public static final BizCode MSG_CODE_ERROR;


    static {
        SUCCESS = new BizCode("000", "success", CodePrefix.SUCCESS);

        ERROR = new BizCode("999", "系统异常", CodePrefix.ERROR);
        RPC_TIMEOUT = new BizCode("998", "RPC调用超时", CodePrefix.ERROR);
        RESOURCE_EXISTS = new BizCode("997", "资源已存在", CodePrefix.ERROR);
        NOT_FOUND = new BizCode("996", "资源不存在", CodePrefix.ERROR);
        THREAD_POOL_PARAM_ERROR = new BizCode("995", "线程池参数未设置", CodePrefix.ERROR);

        DATA_SYNC_ERROR = new BizCode("001", "数据同步异常", CodePrefix.COMMON);
        DATA_INSERT_ERROR = new BizCode("002", "数据插入异常", CodePrefix.COMMON);
        INDEX_CREATE_ERROR = new BizCode("003", "索引创建异常", CodePrefix.COMMON);
        DUPLICATE_OPERATION = new BizCode("004", "重复操作", CodePrefix.COMMON);
        MSG_SEND_ERROR = new BizCode("005", "短信发送异常", CodePrefix.COMMON);
        ILLEGAL_PHONE_NUM = new BizCode("006", "非法手机号", CodePrefix.COMMON);
        MSG_OVER_DAY_TIMES = new BizCode("007", "短信发送超过一天限制", CodePrefix.COMMON);
        MSG_OVER_HOUR_TIMES = new BizCode("008", "短信发送超过一小时限制", CodePrefix.COMMON);
        MSG_OVER_MINUTE_TIMES = new BizCode("009", "短信发送超过一天分钟限制", CodePrefix.COMMON);
        MSG_CODE_EXPIRED = new BizCode("010", "验证码已过期", CodePrefix.COMMON);
        MSG_CODE_ERROR = new BizCode("011", "验证码错误", CodePrefix.COMMON);


    }

    private final String code;

    private final String msg;

    private final CodePrefix codePrefix;

    public BizCode(String code, String msg, CodePrefix codePrefix) {
        this.code = code;
        this.msg = msg;
        this.codePrefix = codePrefix;
    }

    @Override
    public String code() {
        return codePrefix.getCode() + this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
