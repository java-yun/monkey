package com.monkey.common.exception;

import com.monkey.common.code.BizCode;

/**
 * 系统异常
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 15:08
 */
public class SystemException extends RuntimeException {

    private String code;

    public SystemException() {
        super();
    }

    public SystemException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public static SystemException throwException(String code, String msg) {
        return new SystemException(code, msg);
    }

    public static SystemException throwException(BizCode bizCode) {
        return new SystemException(bizCode.code(), bizCode.msg());
    }

    public String getCode() {
        return code;
    }
}
