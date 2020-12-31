package com.monkey.web.request;

import com.monkey.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cms user request
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 14:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends PageRequest {

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户真实姓名
     */
    private String realName;
}
