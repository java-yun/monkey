package com.monkey.web.entity;

import java.util.Date;
import java.util.List;
import lombok.Data;

import javax.persistence.*;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:57
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String mobile;

    private Byte age;

    private String email;

    private String photo;

    private String realName;

    private Byte delFlag;

    private Byte type;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;


    /**
     * ************以下是扩展字段**************
     * 分页查询主键ids
     */
    @Transient
    private List<String> ids;
    /**
     * 角色名称
     */
    @Transient
    private String roleName;

}