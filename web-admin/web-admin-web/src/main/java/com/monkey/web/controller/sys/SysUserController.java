package com.monkey.web.controller.sys;

import com.monkey.common.response.LayUiTableResponse;
import com.monkey.common.response.Response;
import com.monkey.web.entity.SysRole;
import com.monkey.web.entity.SysUser;
import com.monkey.web.request.RoleRequest;
import com.monkey.web.request.UserRequest;
import com.monkey.web.service.sys.SysRoleService;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.service.sys.SysUserService;
import com.monkey.web.utils.RoleUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * cms 用户管理
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/31 14:01
 */
@Controller
@RequestMapping(value = "/user")
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final SysRoleUserMenuService sysRoleUserMenuService;

    public SysUserController(SysUserService sysUserService, SysRoleService sysRoleService, SysRoleUserMenuService sysRoleUserMenuService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysRoleUserMenuService = sysRoleUserMenuService;
    }

    /**
     * 进入列表页面
     * @param model model
     * @return String
     */
    @GetMapping(value = "/showUser")
    @RequiresPermissions("user:show")
    public String showUser(Model model) {
        var roles = sysRoleService.getRoleList(new RoleRequest());
        model.addAttribute("roles", roles);
        return "/system/user/user-list";
    }

    /**
     * 查询列表逻辑
     * @param request request
     * @return LayUiTableResponse<List<SysUser>>
     */
    @GetMapping(value = "/showUserList")
    @ResponseBody
    @RequiresPermissions("user:show")
    public LayUiTableResponse<List<SysUser>> showUser(UserRequest request) {
        var page = this.sysUserService.getUserListWithPage(request);
        return LayUiTableResponse.ok(page.getContent(), page.getSize());
    }

    /**
     * 进入新增页面
     * @param model model
     * @return String
     */
    @GetMapping(value = "/showAddUser")
    @RequiresPermissions("user:add")
    public String goAddUser(Model model) {
        var roles = sysRoleService.getRoleList(new RoleRequest());
        var checkBoxes = RoleUtil.convertRole2CheckBox(roles, null);
        model.addAttribute("boxJson",checkBoxes);
        return "/system/user/user-add";
    }

    /**
     * 新增逻辑
     * @param user user
     * @param roleIds roleIds
     * @return Response<String>
     */
    @PostMapping(value = "/addUser")
    @RequiresPermissions("user:add")
    @ResponseBody
    public Response<String> addUser(SysUser user, Integer[] roleIds) {
        this.sysUserService.addOrUpdateUser(user, roleIds);
        return Response.ok();
    }

    /**
     * 进入编辑页面
     * @param id id
     * @param model model
     * @return String
     */
    @GetMapping(value = "/toUpdateUser")
    @RequiresPermissions("user:update")
    public String toUpdateUser(Integer id, Model model, boolean detail) {
        var sysUser = this.sysUserService.selectByUserId(id);
        var roleIds = this.sysRoleUserMenuService.getRolesByUserId(id);
        List<SysRole> roleList = this.sysRoleService.getRoleList(new RoleRequest());
        var checkBoxes = RoleUtil.convertRole2CheckBox(roleList, roleIds);
        model.addAttribute("user",sysUser);
        model.addAttribute("boxJson",checkBoxes);
        model.addAttribute("detail", detail);
        return "/system/user/user-update";
    }

    /**
     * 编辑（修改）逻辑
     * @param user user
     * @param roleIds roleIds
     * @return Response<String>
     */
    @PostMapping(value = "/updateUser")
    @ResponseBody
    @RequiresPermissions("user:update")
    public Response<String> updateUser(SysUser user, Integer[] roleIds) {
        this.sysUserService.addOrUpdateUser(user, roleIds);
        return Response.ok();
    }

    /**
     * 删除逻辑
     * @param ids ids
     * @return Response<String>
     */
    @PostMapping(value = "/del")
    @ResponseBody
    @RequiresPermissions("user:del")
    public Response<String> del(String ids) {
        this.sysUserService.delUser(ids);
        return Response.ok();
    }

    /**
     * 进入 修改密码 页面
     * @param id id
     * @param type type
     * @param model model
     * @return String
     */
    @GetMapping(value = "/goRePass")
    @RequiresPermissions("user:repass")
    public String goRePass(Integer id, String type, Model model) {
        var sysUser = this.sysUserService.selectByUserId(id);
        model.addAttribute("user",sysUser);
        model.addAttribute("type",type);
        return "/system/user/re-pass";
    }

    /**
     * 修改密码
     * @param id id
     * @param newPwd newPwd
     * @return Response<String>
     */
    @PostMapping(value = "/rePass")
    @ResponseBody
    @RequiresPermissions("user:repass")
    public Response<String> rePass(Integer id, String newPwd) {
        this.sysUserService.resetPass(id, newPwd);
        return Response.ok();
    }

    /**
     * 检查用户名是否存在
     * @param uname uname
     * @return Response<String>
     */
    @GetMapping(value = "/checkUser")
    @ResponseBody
    public Response<Boolean> checkUser(String uname) {
        return Response.ok(this.sysUserService.checkUserName(uname));
    }
}
