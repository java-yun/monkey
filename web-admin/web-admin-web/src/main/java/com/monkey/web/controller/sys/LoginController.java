package com.monkey.web.controller.sys;

import com.monkey.web.bo.CurrentUser;
import com.monkey.web.exception.WebException;
import com.monkey.web.service.sys.LoginService;
import com.monkey.web.utils.CmsSysUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * cms 登录 controller
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:24
 */
@Controller
public class LoginController {

    @Value("${default.login.password:123456}")
    private String defaultPassword;

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    /**
     * 登录跳转
     * @return String
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        var sub = SecurityUtils.getSubject();
        var isRemembered = sub.isRemembered();
        boolean flag = sub.isAuthenticated() || isRemembered;
        if (flag) {
            var user = CmsSysUserUtil.getCurrentUser();
            return isDefaultPwd(user) ? "redirect:/password/modify?userId=" + user.getId() : "/system/main";
        }
        return "/login";
    }

    private boolean isDefaultPwd(CurrentUser user) {
        return CmsSysUserUtil.getMD5(defaultPassword, user.getUsername()).equals(user.getPassword());
    }

    /**
     * 登录
     * @param username username
     * @param password password
     * @param smsCode smsCode
     * @param model model
     * @return String
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String username,String password,String smsCode, Model model){
        String view = "/login";
        try {
            loginService.doLogin(username, password, smsCode);
            var subject = CmsSysUserUtil.getSubject();
            view = isDefaultPwd(CmsSysUserUtil.getCurrentUser()) ? "redirect:/password/modify?userId=" + CmsSysUserUtil.getCurrentUser().getId() : "/system/main";
        } catch (WebException e) {
            model.addAttribute(e.getMessage());
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            model.addAttribute("用户名/密码错误");
        } catch (ExcessiveAttemptsException e) {
            model.addAttribute("登录失败多次，账户锁定10分钟");
        }
        return view;
    }
}
