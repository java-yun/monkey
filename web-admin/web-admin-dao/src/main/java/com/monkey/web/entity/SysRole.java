package com.monkey.web.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:57
 */
@Data
@Entity
@Table(name = "sys_role")
public class SysRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    @Column(name = "p_code")
    private String pCode;

    private String roleName;

    private String description;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    private String remark;

}