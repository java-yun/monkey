package com.monkey.web.request;

import com.monkey.common.request.PageRequest;
import lombok.Data;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/30 14:06
 */
@Data
public class RoleRequest extends PageRequest {

    private String roleName;

    private String description;

}
