package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.Detect;
import com.monkey.web.dao.SysRoleRepository;
import com.monkey.web.entity.SysRole;
import com.monkey.web.entity.SysRoleMenu;
import com.monkey.web.request.RoleRequest;
import com.monkey.web.service.sys.SysRoleService;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.utils.CmsSysUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色 service 实现
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/30 11:52
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;
    
    private final SysRoleUserMenuService sysRoleUserMenuService;

    public SysRoleServiceImpl(SysRoleRepository sysRoleRepository, SysRoleUserMenuService sysRoleUserMenuService) {
        this.sysRoleRepository = sysRoleRepository;
        this.sysRoleUserMenuService = sysRoleUserMenuService;
    }

    @Override
    public List<SysRole> getRoleList(RoleRequest request) {
        var matcher = ExampleMatcher.matching()
                .withMatcher("roleName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("description", ExampleMatcher.GenericPropertyMatcher::contains);
        var sysRole = new SysRole();
        BeanUtils.copyProperties(request, sysRole);
        var example = Example.of(sysRole, matcher);
        if (Objects.nonNull(request.getPage())) {
            //jpa分页是从零开始的
            int page = request.getPage() <= 0 ? 0 : request.getPage() - 1;
            var pageable = PageRequest.of(page, request.getLimit(), Sort.by(Sort.Direction.DESC, "createTime"));
            Page<SysRole> result = this.sysRoleRepository.findAll(example, pageable);
            return result.toList();
        } else {
            return this.sysRoleRepository.findAll(example, Sort.by(Sort.Direction.DESC, "createTime"));
        }
    }

    @Override
    public SysRole selectById(Integer id) {
        return this.sysRoleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateRole(SysRole role, String menuIds) {
        var date = new Date();
        var username = CmsSysUserUtil.getCurrentUser().getUsername();
        if (Objects.isNull(role.getId()) || Objects.isNull(this.selectById(role.getId()))) {
            //新增
            role.setCreateUser(username);
            role.setCreateTime(date);
        }
        role.setUpdateUser(username);
        role.setUpdateTime(date);
        this.sysRoleRepository.save(role);
        //TODO 主键返回
        //查询角色菜单信息
        var existsMenuIds = this.sysRoleUserMenuService.getMenuIdsByRoleId(role.getId());
        var newMenuIds = List.of(menuIds.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        //要删除的角色菜单中间表的菜单id
        var delMenuIds = existsMenuIds.stream().filter(menuId -> !newMenuIds.contains(menuId)).collect(Collectors.toList());
        //要添加的角色菜单中间表数据
        var roleMenuList = newMenuIds.stream().filter(menuId -> !existsMenuIds.contains(menuId)).map(menuId -> {
            var sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(role.getId());
            return sysRoleMenu;
        }).collect(Collectors.toList());

        if (Detect.isNotNullOrEmpty(delMenuIds)) {
            this.sysRoleUserMenuService.deleteRoleMenuByMenuIds(delMenuIds);
        }
        if (Detect.isNotNullOrEmpty(roleMenuList)) {
            this.sysRoleUserMenuService.batchInsertRoleMenu(roleMenuList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(String ids) {
        if (Detect.isNullOrEmpty(ids)) {
            return;
        }
        var roleIds = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        this.sysRoleRepository.deleteByIds(roleIds);
        //删除中间表数据
        this.sysRoleUserMenuService.deleteRoleMenuByRoleIds(roleIds);
    }
}
