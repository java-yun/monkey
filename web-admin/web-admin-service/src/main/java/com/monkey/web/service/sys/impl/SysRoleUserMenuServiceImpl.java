package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.Detect;
import com.monkey.web.constants.WebConstants;
import com.monkey.web.dao.SysMenuRepository;
import com.monkey.web.dao.SysRoleMenuRepository;
import com.monkey.web.dao.SysRoleRepository;
import com.monkey.web.dao.SysRoleUserRepository;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.entity.SysRole;
import com.monkey.web.entity.SysRoleMenu;
import com.monkey.web.entity.SysRoleUser;
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

    private final SysRoleRepository roleRepository;

    private final SysMenuRepository menuRepository;

    private final SysRoleMenuRepository roleMenuRepository;

    private final SysRoleUserRepository roleUserRepository;

    public SysRoleUserMenuServiceImpl(SysRoleRepository roleRepository, SysMenuRepository menuRepository,
                                      SysRoleMenuRepository roleMenuRepository, SysRoleUserRepository roleUserRepository) {
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
        this.roleMenuRepository = roleMenuRepository;
        this.roleUserRepository = roleUserRepository;
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
        return this.roleMenuRepository.getMenuIdsByRoleId(roleId);
    }

    @Override
    public void deleteRoleMenuByMenuId(Integer menuId) {
        this.roleMenuRepository.deleteRoleMenuByMenuId(menuId);
    }

    @Override
    public void deleteRoleMenuByMenuIds(List<Integer> menuIds) {
        this.roleMenuRepository.deleteRoleMenuByMenuIds(menuIds);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void batchInsertRoleMenu(List<SysRoleMenu> roleMenuList) {
        if (Detect.isNullOrEmpty(roleMenuList)) {
            return;
        }
        int size = roleMenuList.size() % WebConstants.JPA_BATCH_INSERT_SIZE == 0 ? roleMenuList.size() / WebConstants.JPA_BATCH_INSERT_SIZE : roleMenuList.size() / WebConstants.JPA_BATCH_INSERT_SIZE + 1;
        log.info("batch insert sys_role_menu times: {}", size);
        IntStream.range(0, size).forEach(i -> {
            var fromIndex = i * WebConstants.JPA_BATCH_INSERT_SIZE;
            var toIndex = i == size - 1 ? roleMenuList.size() : (i + 1) * WebConstants.JPA_BATCH_INSERT_SIZE;
            var list =  roleMenuList.subList(fromIndex, toIndex);
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
        this.roleMenuRepository.deleteRoleMenuByRoleIds(roleIds);
    }

    @Override
    public List<Integer> getUserIdsByRoleId(String roleId) {
        return this.roleUserRepository.getUserIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> getRolesByUserId(Integer userId) {
        return this.roleUserRepository.getRolesByUserId(userId);
    }

    @Override
    public void deleteRoleUserByRoleIds(List<Integer> roleIds) {
        this.roleUserRepository.deleteRoleUserByRoleIds(roleIds);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void batchInsertRoleUser(List<SysRoleUser> roleUserList) {
        if (Detect.isNullOrEmpty(roleUserList)) {
            return;
        }
        int size = roleUserList.size() % WebConstants.JPA_BATCH_INSERT_SIZE == 0 ? roleUserList.size() / WebConstants.JPA_BATCH_INSERT_SIZE : roleUserList.size() / WebConstants.JPA_BATCH_INSERT_SIZE + 1;
        log.info("batch insert sys_role_user times: {}", size);
        IntStream.range(0, size).forEach(i -> {
            var fromIndex = i * WebConstants.JPA_BATCH_INSERT_SIZE;
            var toIndex = i == size - 1 ? roleUserList.size() : (i + 1) * WebConstants.JPA_BATCH_INSERT_SIZE;
            var list =  roleUserList.subList(fromIndex, toIndex);
            var builder = new StringBuilder("insert into sys_role_user(role_id, user_id) values");
            IntStream.range(0, list.size()).forEach(index -> {
                builder.append("(?, ?),");
            });
            builder.deleteCharAt(builder.lastIndexOf(","));
            //设置占位符值
            int paramIndex = 1;
            Query query = entityManager.createNativeQuery(builder.toString());
            for (SysRoleUser roleUser: list) {
                query.setParameter(paramIndex++, roleUser.getRoleId());
                query.setParameter(paramIndex++, roleUser.getUserId());
            }
            query.executeUpdate();
        });
    }

    @Override
    public void deleteRoleUserByUserIds(List<Integer> userIds) {
        this.roleUserRepository.deleteRoleUserByUserIds(userIds);
    }
}
