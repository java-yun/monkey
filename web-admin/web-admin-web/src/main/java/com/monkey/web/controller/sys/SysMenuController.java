package com.monkey.web.controller.sys;

import com.alibaba.fastjson.JSON;
import com.monkey.common.response.Response;
import com.monkey.web.bo.MenuTree;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.service.sys.SysMenuService;
import com.monkey.web.utils.MenuUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * menu controller
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 10:36
 */
@Controller
@RequestMapping(value = "/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @RequiresPermissions("menu:show")
    @GetMapping(value = "/changeMode")
    @ResponseBody
    public Response<List<MenuTree>> changeMode(@RequestParam("code") String code) {
        var menus = sysMenuService.changeMode(code);
        return Response.ok(MenuUtil.convertMenu2Tree(menus));
    }

    /**
     * 显示所有的菜单
     * @param model model
     * @return String
     */
    @RequiresPermissions("menu:show")
    @GetMapping(value = "/showMenu")
    public String showMenu(Model model) {
        var menus = sysMenuService.getAllMenus();
        model.addAttribute("menus", JSON.toJSONString(MenuUtil.convertMenu2Tree(menus)));
        return "/system/menu/menu-list";
    }

    /**
     * 获取除按钮外的其他所有菜单
     * @param model model
     * @return String
     */
    @GetMapping(value = "/showMenuExcludeButton")
    public String showMenuExcludeButton(Model model) {
        var menus = sysMenuService.getAllMenusExcludeButton();
        model.addAttribute("menus", JSON.toJSONString(MenuUtil.convertMenu2Tree(menus)));
        return "/system/menu/menu-list";
    }

    /**
     * 根据条件查询列表
     * @param sysMenu sysMenu
     * @return Response<List<SysMenu>>
     */
    @RequiresPermissions("menu:show")
    @GetMapping(value = "/showMenuByCondition")
    @ResponseBody
    public Response<List<SysMenu>> getMenuByCondition(SysMenu sysMenu) {
        var menus = sysMenuService.getMenuByCondition(sysMenu);
        return Response.ok(menus);
    }

    /**
     * 进入到新增菜单页面
     * @param model model
     * @return String
     */
    @RequiresPermissions("menu:add")
    @GetMapping(value = "/showAddMenu")
    public String addMenu(Model model) {
        var menus = sysMenuService.getAllMenusExcludeButton();
        model.addAttribute("menus", JSON.toJSONString(MenuUtil.convertMenu2Tree(menus)));
        return "/system/menu/menu-add";
    }

    /**
     * 跳转到icon页
     * @return String
     */
    @GetMapping("/icon")
    public String icon() {
        return "/system/menu/icon";
    }

    /**
     * 查看菜单
     * @param model model
     * @param id id
     * @return String
     */
    @RequiresPermissions("menu:edit")
    @GetMapping(value = "/showUpdateMenu")
    public String showUpdateMenu(Model model, Integer id) {
        var sysMenu = sysMenuService.selectById(id);
        model.addAttribute("sysMenu",sysMenu);
        if(Objects.nonNull(sysMenu) && StringUtils.isNotEmpty(sysMenu.getPCode())){
            //获取父菜单
            SysMenu parentMenu = sysMenuService.getByCode(sysMenu.getPCode());
            model.addAttribute("pName", parentMenu.getName());
        }
        return "/system/menu/menu-update";
    }

    /**
     * 新增菜单
     * @param sysMenu sysMenu
     * @return Response<String>
     */
    @RequiresPermissions("menu:add")
    @PostMapping(value = "/addMenu")
    @ResponseBody
    public Response<String> addMenu(SysMenu sysMenu) {
        sysMenuService.addMenu(sysMenu);
        return Response.ok();
    }

    /**
     * 更新菜单
     * @param sysMenu sysMenu
     * @return Response<String>
     */
    @RequiresPermissions("menu:edit")
    @PostMapping(value = "/updateMenu")
    @ResponseBody
    public Response<String> updateMenu(SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return Response.ok();
    }

    /**
     * 删除菜单
     * @param id id
     * @return Response<String>
     */
    @RequiresPermissions("menu:del")
    @PostMapping(value = "/deleteMenu")
    @ResponseBody
    public Response<String> deleteMenu(Integer id) {
        sysMenuService.deleteMenu(id);
        return Response.ok();
    }
}
