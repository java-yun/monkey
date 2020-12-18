package com.monkey.web.exception;

import com.monkey.common.exception.SystemException;

/**
 * web 异常
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 16:41
 */
public class WebException extends SystemException {

    public WebException() {
        super();
    }

    public WebException(String code, String msg) {
        super(code, msg);
    }

    public static WebException throwException(String code, String msg) {
        return new WebException(code, msg);
    }

    public static WebException throwException(WebErrorCode errorCode) {
        return new WebException(errorCode.code(), errorCode.msg());
    }
}
