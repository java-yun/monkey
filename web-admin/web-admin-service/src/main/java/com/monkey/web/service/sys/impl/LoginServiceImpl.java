package com.monkey.web.service.sys.impl;

import com.monkey.common.enums.ModuleEnum;
import com.monkey.common.enums.MsgBizTypeEnum;
import com.monkey.common.service.MsgCodeService;
import com.monkey.common.utils.CommonParamCheckUtils;
import com.monkey.common.utils.Detect;
import com.monkey.web.entity.SysUser;
import com.monkey.web.exception.WebErrorCode;
import com.monkey.web.exception.WebException;
import com.monkey.web.service.sys.LoginService;
import com.monkey.web.service.sys.SysUserService;
import com.monkey.web.utils.CmsSysUserUtil;
import com.monkey.web.utils.WebParamCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:49
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${monkey.web.is-debug:true}")
    private boolean isDebug;

    private final SysUserService sysUserService;

    private final MsgCodeService msgCodeService;

    public LoginServiceImpl(SysUserService sysUserService, MsgCodeService msgCodeService) {
        this.sysUserService = sysUserService;
        this.msgCodeService = msgCodeService;
    }

    @Override
    public void doLogin(String username, String password, String smsCode) {
        var sysUser = validateUser(username);
        if (!isDebug) {
            this.msgCodeService.checkMsg(sysUser.getMobile(), smsCode, ModuleEnum.CMS_WEB.getModule(), MsgBizTypeEnum.CMS_LOGIN.getType());
        }
        var subject = CmsSysUserUtil.getSubject();
        log.info("===> request password is:{}", password);
        subject.login(new UsernamePasswordToken(username.trim(), password));
    }

    @Override
    public void sendMsg(String username) {
        if (isDebug) {
            return;
        }
        var sysUser = this.validateUser(username);
        if (Detect.isNullOrEmpty(sysUser.getMobile())) {
            log.error("phone not bind, username:{}", username);
            throw WebException.throwException(WebErrorCode.PHONE_NOT_BIND);
        }
        new CommonParamCheckUtils.Builder().phoneNum(sysUser.getMobile()).build().checkParam();
        //发送验证码
        this.msgCodeService.sendAliyunMessage(sysUser.getMobile(), ModuleEnum.CMS_WEB.getModule(), MsgBizTypeEnum.CMS_LOGIN.getType());
    }

    @Override
    public void resetPassword(Integer id, String pass, String newPwd) {
        new WebParamCheckUtils.Builder().cmsPwd(Detect.isNullOrEmpty(pass) ? pass : pass.trim()).cmsPwd(Detect.isNullOrEmpty(newPwd) ? newPwd : newPwd.trim()).build().checkParam();
        var sysUser = this.validateUser(id);
        //校验新老密码是否一样
        if (pass.trim().equals(newPwd.trim())) {
            log.error("same old and new passwords, password:{}, new password:{}", pass, newPwd);
            throw WebException.throwException(WebErrorCode.NEW_PWD_EQUALS_OLD);
        }
        //老密码是否正确
        if (!CmsSysUserUtil.getMD5(pass, sysUser.getUsername()).equals(sysUser.getPassword())) {
            log.error("old password error, password:{}", pass);
            throw WebException.throwException(WebErrorCode.OLD_PWD_ERROR);
        }
        //更新密码
        sysUser.setPassword(CmsSysUserUtil.getMD5(newPwd, sysUser.getUsername()));
        sysUser.setUpdateUser(CmsSysUserUtil.getCurrentUser().getUsername());
        sysUser.setUpdateTime(new Date());
        this.sysUserService.updateById(sysUser);
        //退出登陆
        CmsSysUserUtil.getSubject().logout();
    }

    private SysUser validateUser(String username) {
        var sysUser = this.sysUserService.findEnableUserByUsername(username);
        if (Objects.isNull(sysUser)) {
            log.error("user not exists, username:{}", username);
            throw WebException.throwException(WebErrorCode.SYS_USER_NOT_EXITS);
        }
        return sysUser;
    }

    private SysUser validateUser(Integer id) {
        var sysUser = this.sysUserService.selectByUserId(id);
        if (Objects.isNull(sysUser)) {
            log.error("user not exists, id:{}", id);
            throw WebException.throwException(WebErrorCode.SYS_USER_NOT_EXITS);
        }
        return sysUser;
    }
}
