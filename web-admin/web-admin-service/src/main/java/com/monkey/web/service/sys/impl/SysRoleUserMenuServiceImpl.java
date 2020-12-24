package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.Detect;
import com.monkey.common.utils.IsValid;
import com.monkey.web.dao.SysMenuRepository;
import com.monkey.web.dao.SysRoleRepository;
import com.monkey.web.dao.SysUserRepository;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.entity.SysRole;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 13:52
 */
@Service
public class SysRoleUserMenuServiceImpl implements SysRoleUserMenuService {

    private final SysUserRepository userRepository;

    private final SysRoleRepository roleRepository;

    private final SysMenuRepository menuRepository;

    public SysRoleUserMenuServiceImpl(SysUserRepository userRepository, SysRoleRepository roleRepository, SysMenuRepository menuRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return this.roleRepository.getRolesByUsername(username);
    }

    @Override
    public List<SysMenu> getButtonsByUsername(String username) {
        //查询用户角
        var roleIds = this.getRolesByUsername(username).stream().map(SysRole::getId).collect(Collectors.toList());
        if (Detect.isNullOrEmpty(roleIds)) {
            return null;
        }
        return this.menuRepository.getMenuByRoleIds(roleIds);
    }

    @Override
    public List<SysMenu> getOneLevel(String username) {
        //查询用户角色
        var roleIds = this.getRolesByUsername(username).stream().map(SysRole::getId).collect(Collectors.toList());
        if (Detect.isNullOrEmpty(roleIds)) {
            return null;
        }
        return this.menuRepository.selectByRolesAndParentCode(roleIds, "");
    }

    @Override
    public List<SysMenu> getUserMenusByParent(String username, String pCode, Byte isButton) {
        //查询用户角色
        var roleIds = this.getRolesByUsername(username).stream().map(SysRole::getId).collect(Collectors.toList());
        if (Detect.isNullOrEmpty(roleIds)) {
            return null;
        }
        return this.menuRepository.selectMenusByRolesAndParentCode(roleIds, pCode, isButton);
    }
}
