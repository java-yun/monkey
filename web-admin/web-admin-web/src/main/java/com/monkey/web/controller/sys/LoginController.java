package com.monkey.web.controller.sys;

import com.monkey.common.response.Response;
import com.monkey.web.bo.CurrentUser;
import com.monkey.web.exception.WebException;
import com.monkey.web.service.sys.LoginService;
import com.monkey.web.service.sys.SysMenuService;
import com.monkey.web.service.sys.SysUserService;
import com.monkey.web.utils.CmsSysUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * cms 登录 controller
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:24
 */
@Controller
public class LoginController {

    @Value("${monkey.web.default-password:123456}")
    private String defaultPassword;

    private final LoginService loginService;

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    public LoginController(LoginService loginService, SysUserService sysUserService, SysMenuService sysMenuService) {
        this.loginService = loginService;
        this.sysUserService = sysUserService;
        this.sysMenuService = sysMenuService;
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
            return isDefaultPwd(user) ? "redirect:/password/modify?userId=" + user.getId() : "system/main";
        }
        return "system/login";
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
        String view = "system/login";
        try {
            loginService.doLogin(username, password, smsCode);
            view = isDefaultPwd(CmsSysUserUtil.getCurrentUser()) ? "redirect:/password/modify?userId=" + CmsSysUserUtil.getCurrentUser().getId() : "redirect:/main";
        } catch (WebException e) {
            model.addAttribute(e.getMessage());
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            model.addAttribute("用户名/密码错误");
        } catch (ExcessiveAttemptsException e) {
            model.addAttribute("登录失败多次，账户锁定10分钟");
        }
        return view;
    }

    /**
     * 登陆发送验证码
     * @param username username
     * @return Response<String>
     */
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> sendMsg(String username){
        loginService.sendMsg(username);
        return Response.ok();
    }

    /**
     * 登陆成功 跳转到主页
     * @param model model
     * @return String
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model) {
        var userVisibleMenu = this.sysMenuService.getUserVisibleMenu();
        model.addAttribute("topMenus", userVisibleMenu.getTopMenus());
        model.addAttribute("leftMenus", userVisibleMenu.getLeftMenus());
        return "system/main";
    }

    /**
     * 退出登陆
     * @return String
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        CmsSysUserUtil.getSubject().logout();
        return "system/login";
    }

    /**
     * 进入修改密码页面
     * @param userId 用户id
     * @param model model
     * @return String
     */
    @RequestMapping(value = "/password/modify", method = RequestMethod.GET)
    public String modifyPassword(Integer userId, Model model) {
        var sysUser = this.sysUserService.selectByUserId(userId);
        model.addAttribute("user", sysUser);
        return "system/modify_password";
    }

    /**
     * 登陆修改密码逻辑
     * @param id 用户id
     * @param pass 原始密码
     * @param newPwd 新密码
     * @return Response<String>
     */
    @RequestMapping(value = "/password/rePass", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> rePass(Integer id, String pass, String newPwd) {
        this.loginService.resetPassword(id, pass, newPwd);
        return Response.ok();
    }
}
