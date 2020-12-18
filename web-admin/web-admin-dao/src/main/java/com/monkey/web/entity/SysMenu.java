package com.monkey.web.entity;

import java.util.Date;
import lombok.Data;

import javax.persistence.*;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/17 11:57
 */
@Entity
@Table(name = "sys_menu")
@Data
public class SysMenu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String code;

    @Column(name = "p_code")
    private String pCode;

    private Byte level;

    private String url;

    @Column(name = "order_num")
    private Short orderNum;

    private String icon;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    private String permission;

    @Column(name = "menu_type")
    private Byte menuType;

    /**
     * ************以下是扩展字段**************
     * 是否展开
     */
    @Transient
    private boolean open  = true;

    /**
     * 是否选中
     */
    @Transient
    private boolean checked = false;

}