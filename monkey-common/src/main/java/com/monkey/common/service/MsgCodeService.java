package com.monkey.common.service;

/**
 * 短信验证码发送 service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 10:19
 */
public interface MsgCodeService {

    /**
     * 发送短信验证码
     * @param phoneNum phoneNum
     * @param module module
     * @param bizType bizType
     */
    void sendAliyunMessage(String phoneNum, String module, String bizType);

    /**
     * 校验验证码
     * @param phoneNum phoneNum
     * @param smsCode smsCode
     * @param module module
     * @param bizType bizType
     */
    void checkMsg(String phoneNum, String smsCode, String module, String bizType);
}
