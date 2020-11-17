package com.monkey.common.code;

/**
 * 响应状态码前缀
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 15:50
 */
public class CodePrefix {

    /**
     * 成功状态码前缀
     */
    public static final CodePrefix SUCCESS = new CodePrefix("000");
    /**
     * 通用状态码前缀
     */
    public static final CodePrefix COMMON = new CodePrefix("100");
    /**
     * 未知异常前缀
     */
    public static final CodePrefix ERROR = new CodePrefix("999");

    /**
     * 状态码前缀
     */
    private final String code;

    public CodePrefix(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
