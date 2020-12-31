package com.monkey.web.controller.sys;

import com.alibaba.fastjson.JSON;
import com.monkey.common.response.LayUiTableResponse;
import com.monkey.common.response.Response;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.entity.SysRole;
import com.monkey.web.request.RoleRequest;
import com.monkey.web.service.sys.SysMenuService;
import com.monkey.web.service.sys.SysRoleService;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.utils.MenuUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * role controller
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/30 11:55
 */
@Controller
@RequestMapping(value = "/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final SysRoleUserMenuService sysRoleUserMenuService;

    public SysRoleController(SysRoleService sysRoleService, SysMenuService sysMenuService, SysRoleUserMenuService sysRoleUserMenuService) {
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
        this.sysRoleUserMenuService = sysRoleUserMenuService;
    }

    /**
     * 跳转角色列表页面
     * @return String
     */
    @RequiresPermissions("role:show")
    @GetMapping(value = "/showRole")
    public String showRole() {
        return "/system/role/role-list";
    }

    /**
     * 查询角色列表
     * @param request request
     * @return LayUiTableResponse<List<SysRole>>
     */
    @RequiresPermissions("role:show")
    @GetMapping(value = "/showRoleList")
    @ResponseBody
    public LayUiTableResponse<List<SysRole>> showRoleList(RoleRequest request) {
        Page<SysRole> page = sysRoleService.getRoleListWithPage(request);
        return LayUiTableResponse.ok(page.getContent(), page.getSize());
    }

    /**
     * 进入新增页面
     * @param model model
     * @return String
     */
    @RequiresPermissions("role:add")
    @GetMapping(value = "/showAddRole")
    public String toAddRole(Model model) {
        List<SysMenu> menus = this.sysMenuService.getAllMenus();
        model.addAttribute("menus", JSON.toJSONString(MenuUtil.convertMenu2Tree(menus)));
        return "/system/role/role-add";
    }

    /**
     * 进入到更新角色页面
     * @param id id
     * @param model model
     * @param detail detail
     * @return String
     */
    @RequiresPermissions(value = {"role:update", "role:view"}, logical = Logical.OR)
    @GetMapping(value = "/toUpdateRole")
    public String toUpdateRole(Integer id, Model model, boolean detail) {
        var menus = this.sysMenuService.getAllMenus();
        var menuIds = this.sysRoleUserMenuService.getMenuIdsByRoleId(id);
        menus.forEach(menu -> {
            if (menuIds.contains(menu.getId())) {
                menu.setChecked(true);
            }
        });
        var sysRole = this.sysRoleService.selectById(id);
        model.addAttribute("menus", JSON.toJSONString(MenuUtil.convertMenu2Tree(menus)));
        model.addAttribute("role", sysRole);
        model.addAttribute("detail", detail);
        return "/system/role/role-update";
    }

    /**
     * 更新角色信息
     * @param role role
     * @param menus menus
     * @return Response<String>
     */
    @RequiresPermissions(value = {"role:update", "role:add"}, logical = Logical.OR)
    @PostMapping(value = "/updateRole")
    @ResponseBody
    public Response<String> updateUser(SysRole role, String menus) {
        this.sysRoleService.addOrUpdateRole(role, menus);
        return Response.ok();
    }

    /**
     * 删除角色
     * @param ids ids
     * @return Response<String>
     */
    @PostMapping(value = "/del")
    @ResponseBody
    @RequiresPermissions("role:del")
    public Response<String> del(String ids){
        this.sysRoleService.deleteRoleById(ids);
        return Response.ok();
    }
}
