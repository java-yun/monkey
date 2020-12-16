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
    public static final BizCode DATA_SYNC_ERROR;
    public static final BizCode DATA_INSERT_ERROR;
    public static final BizCode INDEX_CREATE_ERROR;
    public static final BizCode DUPLICATE_OPERATION;
    public static final BizCode RESOURCE_EXISTS;
    public static final BizCode NOT_FOUND;

    static {
        SUCCESS = new BizCode("000", "success", CodePrefix.SUCCESS);
        ERROR = new BizCode("999", "system exception", CodePrefix.ERROR);
        RPC_TIMEOUT = new BizCode("998", "RPC invoke timeout", CodePrefix.ERROR);
        DATA_SYNC_ERROR = new BizCode("997", "data sync exception", CodePrefix.ERROR);
        DATA_INSERT_ERROR = new BizCode("996", "data sync exception", CodePrefix.ERROR);
        INDEX_CREATE_ERROR = new BizCode("995", "index create exception", CodePrefix.ERROR);

        DUPLICATE_OPERATION = new BizCode("001", "duplicate operation", CodePrefix.COMMON);
        RESOURCE_EXISTS = new BizCode("002", "resources already exists", CodePrefix.COMMON);
        NOT_FOUND = new BizCode("003", "resources not found", CodePrefix.COMMON);

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
