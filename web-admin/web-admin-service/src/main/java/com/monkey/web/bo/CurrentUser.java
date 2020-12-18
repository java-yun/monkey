package com.monkey.web.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jiangyun
 */
@Data
public class CurrentUser implements Serializable {

    private Integer id;

    private String pId;

    private String cpId;

    private String username;

    private String password;

    private Byte age;

    private String email;

    private String photo;

    private String realName;

    private String createBy;

    private String updateBy;

    private Date createDate;

    private Date updateDate;

    private Byte delFlag;

    private String mobile;

    private Byte type;

    private  List<CurrentRole> roles;

    private List<CurrentPermitButton> permitButtons;

    public CurrentUser(Integer id, String username, Byte age, String email, String photo,
                       String realName) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
        this.photo = photo;
        this.realName = realName;
    }

    public CurrentUser() {
    }


}