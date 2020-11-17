package com.monkey.common.code;

/**
 * 猴子电商 状态码前缀
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 16:39
 */
public class MonkeyCodePrefix extends CodePrefix {

    /**
     * 用户响应状态码前缀
     */
    public static final MonkeyCodePrefix USER = new MonkeyCodePrefix("200");
    /**
     * 账户响应状态码前缀
     */
    public static final MonkeyCodePrefix ACCOUNT = new MonkeyCodePrefix("300");
    /**
     * 商品响应状态码前缀
     */
    public static final MonkeyCodePrefix PRODUCT = new MonkeyCodePrefix("400");

    public MonkeyCodePrefix(String code) {
        super(code);
    }
}
