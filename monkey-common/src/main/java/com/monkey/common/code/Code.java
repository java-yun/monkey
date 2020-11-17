package com.monkey.common.code;

/**
 * 异常状态码接口
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/17 16:03
 */
public interface Code {

    /**
     * 获取异常状态码
     * @return String
     */
    String code();

    /**
     * 获取异常信息
     * @return String
     */
    String msg();
}
