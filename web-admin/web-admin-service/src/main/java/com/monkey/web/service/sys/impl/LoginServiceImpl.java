package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.IsValid;
import com.monkey.web.dao.SysUserRepository;
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

    public LoginServiceImpl(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doLogin(String username, String password, String smsCode) {
        var sysUser = this.userRepository.findByUsernameAndDelFlag(username, Byte.parseByte(IsValid.valid.getValue()));
        if (Objects.isNull(sysUser)) {
            log.error("user not exists, username:{}", username);
            throw WebException.throwException(WebErrorCode.SYS_USER_NOT_EXITS);
        }
        if (!isDebug) {
            //校验短信验证码 TODO
        }
        var subject = CmsSysUserUtil.getSubject();
        log.info("===> request password is:{}", password);
        subject.login(new UsernamePasswordToken(username.trim(), password));
    }
}
