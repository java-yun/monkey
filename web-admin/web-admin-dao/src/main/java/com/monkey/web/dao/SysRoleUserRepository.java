package com.monkey.web.dao;

import com.monkey.web.entity.SysRoleUser;
import com.monkey.web.id.RoleUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 10:37
 */
public interface SysRoleUserRepository extends JpaRepository<SysRoleUser, RoleUserId> {

    /**
     * 根据 roleId 查询 userIds
     * @param roleId roleId
     * @return List<Integer>
     */
    @Query(value = "select user_id from sys_role_user where role_id = ?1", nativeQuery = true)
    List<Integer> getUserIdsByRoleId(String roleId);

    /**
     * 根据 userId 查询 roleIds
     * @param userId userId
     * @return List<Integer>
     */
    @Query(value = "select role_id from sys_role_user where user_id = ?1", nativeQuery = true)
    List<Integer> getRolesByUserId(Integer userId);

    /**
     * 删除 RoleUser 根据 roleIds
     * @param roleIds roleIds
     */
    @Modifying
    @Query(value = "delete from sys_role_user where role_id in(?1)", nativeQuery = true)
    void deleteRoleUserByRoleIds(List<Integer> roleIds);

    /**
     * 删除 RoleUser 根据 userIds
     * @param userIds userIds
     */
    @Modifying
    @Query(value = "delete from sys_role_user where user_id in(?1)", nativeQuery = true)
    void deleteRoleUserByUserIds(List<Integer> userIds);
}
