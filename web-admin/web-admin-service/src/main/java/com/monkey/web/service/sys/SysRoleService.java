package com.monkey.web.service.sys;

import com.monkey.web.entity.SysRole;
import com.monkey.web.request.RoleRequest;

import java.util.List;

/**
 * 角色 service
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/30 11:51
 */
public interface SysRoleService {

    /**
     * 查询角色  列表
     * @param request request
     * @return List<SysRole>
     */
    List<SysRole> getRoleList(RoleRequest request);

    /**
     * 根据主键查询
     * @param id id
     * @return SysRole
     */
    SysRole selectById(Integer id);

    /**
     * 添加或者  更新  角色
     * @param role role
     * @param menuIds menuIds
     */
    void addOrUpdateRole(SysRole role, String menuIds);

    /**
     * 根据主键删除角色
     * @param ids ids
     */
    void deleteRoleById(String ids);
}
