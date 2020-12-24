package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.IsValid;
import com.monkey.web.dao.SysUserRepository;
import com.monkey.web.entity.SysUser;
import com.monkey.web.service.sys.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/23 18:05
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;

    public SysUserServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public SysUser selectByUserId(Integer userId) {
        return this.sysUserRepository.findById(userId).orElse(null);
    }

    @Override
    public SysUser findEnableUserByUsername(String username) {
        return this.sysUserRepository.findByUsernameAndDelFlag(username, Byte.parseByte(IsValid.valid.getValue()));
    }

    @Override
    public void updateById(SysUser sysUser) {
        this.sysUserRepository.save(sysUser);
    }
}
