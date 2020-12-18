package com.monkey.web.exception;

import com.monkey.common.code.BizCode;
import com.monkey.common.code.CodePrefix;
import com.monkey.common.code.MonkeyCodePrefix;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 16:57
 */
public class WebErrorCode extends BizCode {

    public static final WebErrorCode TEST = new WebErrorCode("000", "test");
    public static final WebErrorCode SYS_USER_NOT_EXITS = new WebErrorCode("001", "sys user not exits");
    public static final WebErrorCode USER_NAME_IS_EMPTY = new WebErrorCode("002", "user name is empty");
    public static final WebErrorCode PHONE_NOT_BIND = new WebErrorCode("003", "phone not bind");
    public static final WebErrorCode NEW_PWD_EQUALS_OLD = new WebErrorCode("004", "new password equals old password");
    public static final WebErrorCode OLD_PWD_ERROR = new WebErrorCode("005", "old password is wrong");
    public static final WebErrorCode PARAM_ERROR = new WebErrorCode("006", "param error");

    private WebErrorCode(String code, String msg, CodePrefix codePrefix) {
        super(code, msg, codePrefix);
    }

    public WebErrorCode(String code, String msg) {
        this(code, msg, MonkeyCodePrefix.WEB);
    }
}
