package com.monkey.web.dao;

import com.monkey.web.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/18 13:36
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    /**
     * 根据 username 和 delFlag 查询
     * @param username username
     * @param delFlag delFlag
     * @return SysUser
     */
    SysUser findByUsernameAndDelFlag(String username, byte delFlag);
}
