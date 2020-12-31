package com.monkey.web.service.sys;

import com.monkey.web.entity.SysUser;
import com.monkey.web.request.UserRequest;
import org.springframework.data.domain.Page;

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

    /**
     * cms 用户列表 page
     * @param request request
     * @return Page<SysUser>
     */
    Page<SysUser> getUserListWithPage(UserRequest request);

    /**
     * 添加用户
     * @param user user
     * @param roleIds roleIds
     */
    void addOrUpdateUser(SysUser user, Integer[] roleIds);

    /**
     * 删除用户
     * @param ids ids
     */
    void delUser(String ids);

    /**
     * 修改密码
     * @param id id
     * @param newPwd newPwd
     */
    void resetPass(Integer id, String newPwd);

    /**
     * 检查用户是否存在
     * @param uname uname
     * @return Boolean
     */
    Boolean checkUserName(String uname);
}
