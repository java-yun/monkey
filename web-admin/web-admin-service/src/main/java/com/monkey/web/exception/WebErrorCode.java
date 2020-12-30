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
    public static final WebErrorCode SYS_USER_NOT_EXITS = new WebErrorCode("001", "用户不存在");
    public static final WebErrorCode USER_NAME_IS_EMPTY = new WebErrorCode("002", "用户名为空");
    public static final WebErrorCode PHONE_NOT_BIND = new WebErrorCode("003", "手机号未绑定");
    public static final WebErrorCode NEW_PWD_EQUALS_OLD = new WebErrorCode("004", "新老密码相同");
    public static final WebErrorCode OLD_PWD_ERROR = new WebErrorCode("005", "老密码错误");
    public static final WebErrorCode PARAM_ERROR = new WebErrorCode("006", "参数异常");
    public static final WebErrorCode PWD_LENGTH_ERROR = new WebErrorCode("007", "密码长度不规范");
    public static final WebErrorCode MENU_NAME_IS_EMPTY = new WebErrorCode("008", "菜单名称为空");
    public static final WebErrorCode ILLEGAL_MENU_TYPE = new WebErrorCode("009", "菜单类型为空");
    public static final WebErrorCode MENU_LEVEL_IS_EMPTY = new WebErrorCode("010", "菜单级别为空");
    public static final WebErrorCode PARENT_CODE_EMPTY = new WebErrorCode("011", "父菜单为空");

    private WebErrorCode(String code, String msg, CodePrefix codePrefix) {
        super(code, msg, codePrefix);
    }

    public WebErrorCode(String code, String msg) {
        this(code, msg, MonkeyCodePrefix.WEB);
    }
}
