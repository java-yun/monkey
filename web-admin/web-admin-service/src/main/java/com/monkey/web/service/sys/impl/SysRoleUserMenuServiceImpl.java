package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.Detect;
import com.monkey.common.utils.IsValid;
import com.monkey.web.constants.WebConstants;
import com.monkey.web.dao.SysMenuRepository;
import com.monkey.web.dao.SysRoleRepository;
import com.monkey.web.dao.SysUserRepository;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.entity.SysRole;
import com.monkey.web.entity.SysRoleMenu;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 13:52
 */
@Slf4j
@Service
public class SysRoleUserMenuServiceImpl implements SysRoleUserMenuService {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public List<Integer> getMenuIdsByRoleId(Integer roleId) {
        return this.menuRepository.getMenuIdsByRoleId(roleId);
    }

    @Override
    public void deleteRoleMenuByMenuIds(List<Integer> menuIds) {
        this.menuRepository.deleteRoleMenuByMenuIds(menuIds);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void batchInsertRoleMenu(List<SysRoleMenu> roleMenuList) {
        if (Detect.isNullOrEmpty(roleMenuList)) {
            return;
        }
        int size = roleMenuList.size() % WebConstants.JPA_BATCH_INSERT_SIZE == 0 ? roleMenuList.size() / WebConstants.JPA_BATCH_INSERT_SIZE : roleMenuList.size() / WebConstants.JPA_BATCH_INSERT_SIZE + 1;
        log.info("batch insert times: {}", size);
        IntStream.range(0, size).forEach(i -> {
            var list = roleMenuList.subList(i * WebConstants.JPA_BATCH_INSERT_SIZE, (i + 1) * WebConstants.JPA_BATCH_INSERT_SIZE - 1);
            var builder = new StringBuilder("insert into sys_role_menu(role_id, menu_id) values");
            IntStream.range(0, list.size()).forEach(index -> {
                builder.append("(?, ?),");
            });
            builder.deleteCharAt(builder.lastIndexOf(","));
            //设置占位符值
            int paramIndex = 1;
            Query query = entityManager.createNativeQuery(builder.toString());
            for (SysRoleMenu roleMenu: list) {
                query.setParameter(paramIndex++, roleMenu.getRoleId());
                query.setParameter(paramIndex++, roleMenu.getMenuId());
            }
            query.executeUpdate();
        });
    }

    @Override
    public void deleteRoleMenuByRoleIds(List<Integer> roleIds) {
        this.menuRepository.deleteRoleMenuByRoleIds(roleIds);
    }
}
