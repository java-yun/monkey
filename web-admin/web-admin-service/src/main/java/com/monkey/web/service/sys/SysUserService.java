package com.monkey.web.service.sys;

import com.monkey.web.entity.SysUser;

/**
 * 系统  用户 service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/23 18:05
 */
public interface SysUserService {

    /**
     * 根据 userId 查询
     * @param userId userId
     * @return SysUser
     */
    SysUser selectByUserId(Integer userId);

    /**
     * 根据 username 查询可用用户
     * @param username username
     * @return SysUser
     */
    SysUser findEnableUserByUsername(String username);

    /**
     * 根据主键 更新
     * @param sysUser sysUser
     */
    void updateById(SysUser sysUser);
}
