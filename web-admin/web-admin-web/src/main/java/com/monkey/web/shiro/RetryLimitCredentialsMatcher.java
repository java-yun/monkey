package com.monkey.web.shiro;

import cn.hutool.core.util.StrUtil;
import com.monkey.common.repository.RedisRepository;
import com.monkey.rpc.constants.WebRedisKeyConstants;
import com.monkey.web.constants.WebConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 验证器，增加了登录次数校验功能
 * 限制尝试登陆次数,防止暴力破解
 * @author jiangyun
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        var username = (String) token.getPrincipal();
        //retry count + 1
        var errorTimes = redisRepository.getString(WebRedisKeyConstants.CMS_ERROR_LOGIN_TIMES + username);
        int times = StringUtils.isEmpty(errorTimes) ? 0 : Integer.parseInt(errorTimes);
        if (times >= WebConstants.MAX_RETRY_COUNT) {
            throw new ExcessiveAttemptsException(StrUtil.format("username: {} tried to login more than {} times in period", username, WebConstants.MAX_RETRY_COUNT));
        }
        var matches = super.doCredentialsMatch(token, info);
        if (matches) {
            redisRepository.remove(WebRedisKeyConstants.CMS_ERROR_LOGIN_TIMES + username);
        } else {
            redisRepository.incrementOneExpire(WebRedisKeyConstants.CMS_ERROR_LOGIN_TIMES + username, WebConstants.ERROR_TIMES_EXPIRE_SECONDS);
        }
        return matches;
    }
}  