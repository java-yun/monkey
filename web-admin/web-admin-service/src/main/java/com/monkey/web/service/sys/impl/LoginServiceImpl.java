package com.monkey.web.service.sys.impl;

import com.monkey.common.enums.ModuleEnum;
import com.monkey.common.enums.MsgBizTypeEnum;
import com.monkey.common.service.MsgCodeService;
import com.monkey.common.utils.CommonParamCheckUtils;
import com.monkey.common.utils.IsValid;
import com.monkey.web.dao.SysUserRepository;
import com.monkey.web.entity.SysUser;
import com.monkey.web.exception.WebErrorCode;
import com.monkey.web.exception.WebException;
import com.monkey.web.service.sys.LoginService;
import com.monkey.web.utils.CmsSysUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 15:49
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${monkey.web.is_debug:true}")
    private boolean isDebug;

    private final SysUserRepository userRepository;

    private final MsgCodeService msgCodeService;

    public LoginServiceImpl(SysUserRepository userRepository, MsgCodeService msgCodeService) {
        this.userRepository = userRepository;
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

    private SysUser validateUser(String username) {
        var sysUser = this.userRepository.findByUsernameAndDelFlag(username, Byte.parseByte(IsValid.valid.getValue()));
        if (Objects.isNull(sysUser)) {
            log.error("user not exists, username:{}", username);
            throw WebException.throwException(WebErrorCode.SYS_USER_NOT_EXITS);
        }
        return sysUser;
    }

    @Override
    public void sendMsg(String username) {
        if (isDebug) {
            return;
        }
        var sysUser = this.validateUser(username);
        new CommonParamCheckUtils.Builder().phoneNum(sysUser.getMobile()).build().checkParam();
        //发送验证码
        this.msgCodeService.sendAliyunMessage(sysUser.getMobile(), ModuleEnum.CMS_WEB.getModule(), MsgBizTypeEnum.CMS_LOGIN.getType());
    }
}
