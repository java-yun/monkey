package com.monkey.web.service.sys;

/**
 * 登录 service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:48
 */
public interface LoginService {

    /**
     * 登录
     * @param username username
     * @param password password
     * @param smsCode smsCode
     */
    void doLogin(String username, String password, String smsCode);

    /**
     * 发送登录短信验证码
     * @param username username
     */
    void sendMsg(String username);

    /**
     * 重置密码
     * @param id 用户id
     * @param pass 原密码
     * @param newPwd 新密码
     */
    void resetPassword(Integer id, String pass, String newPwd);
}
