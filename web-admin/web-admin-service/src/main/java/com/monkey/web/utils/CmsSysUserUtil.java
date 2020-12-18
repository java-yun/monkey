package com.monkey.web.utils;

import com.monkey.web.bo.CurrentUser;
import com.monkey.web.constants.WebConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


/**
 * cms 用户工具类
 * @author jiangyun
 */
public class CmsSysUserUtil {
    public static String SHIRO_CURRENT_USER = "currentUser" ;

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Session getSession(){
        return getSubject().getSession();
    }

    /**
     * 获取当前用户信息
     * @return CurrentUser
     */
    public static CurrentUser getCurrentUser(){
        return (CurrentUser) getSession().getAttribute(SHIRO_CURRENT_USER);
    }

    public static String getMD5(String msg, String salt) {
        return new Md5Hash(msg, salt, WebConstants.CMS_PASSWORD_HASH_ITERATIONS).toString();
    }


}
