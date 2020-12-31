package com.monkey.web.dao;

import com.monkey.web.entity.SysRoleMenu;
import com.monkey.web.id.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 10:21
 */
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, RoleMenuId> {

    /**
     * getMenuIdsByRoleId
     * @param roleId roleId
     * @return List<Integer>
     */
    @Query(value = "select menu_id from sys_role_menu where role_id = ?1", nativeQuery = true)
    List<Integer> getMenuIdsByRoleId(Integer roleId);

    /**
     * 根据menuId删除
     * @param menuId menuId
     * @return Long
     */
    @Modifying
    @Query(value = "delete from sys_role_menu where menu_id = ?1", nativeQuery = true)
    void deleteRoleMenuByMenuId(Integer menuId);

    /**
     * 根据menuIds删除
     * @param menuIds menuIds
     * @return Long
     */
    @Modifying
    @Query(value = "delete from sys_role_menu where menu_id in (?1)", nativeQuery = true)
    void deleteRoleMenuByMenuIds(List<Integer> menuIds);

    /**
     * 根据roleIds删除
     * @param roleIds roleIds
     * @return Long
     */
    @Modifying
    @Query(value = "delete from sys_role_menu where role_id in (?1)", nativeQuery = true)
    void deleteRoleMenuByRoleIds(List<Integer> roleIds);
}
