package com.monkey.web.dao;

import com.monkey.web.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 13:36
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Integer>, JpaSpecificationExecutor<SysUser> {

    /**
     * 根据 username 和 delFlag 查询
     * @param username username
     * @param delFlag delFlag
     * @return SysUser
     */
    SysUser findByUsernameAndDelFlag(String username, byte delFlag);

    /**
     * 根据 ids 删除
     * @param userIds userIds
     */
    @Modifying
    @Query(value = "delete from sys_user where id in(?1)", nativeQuery = true)
    void deleteByIds(List<Integer> userIds);

    /**
     * 根据 username 查询
     * @param username username
     * @return SysUser
     */
    SysUser findByUsername(String username);
}
