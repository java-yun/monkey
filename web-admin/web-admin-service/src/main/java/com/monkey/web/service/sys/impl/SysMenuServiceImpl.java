package com.monkey.web.service.sys.impl;

import com.monkey.common.utils.Detect;
import com.monkey.common.utils.IsValid;
import com.monkey.web.bo.UserVisibleMenu;
import com.monkey.web.dao.SysMenuRepository;
import com.monkey.web.dao.SysRoleMenuRepository;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.exception.WebErrorCode;
import com.monkey.web.exception.WebException;
import com.monkey.web.service.sys.SysMenuService;
import com.monkey.web.service.sys.SysRoleUserMenuService;
import com.monkey.web.utils.CmsSysUserUtil;
import com.monkey.web.utils.MenuUtil;
import com.monkey.web.utils.WebParamCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * menu service impl
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 10:36
 */
@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {

    private final SysRoleUserMenuService sysRoleUserMenuService;

    private final SysMenuRepository sysMenuRepository;

    public SysMenuServiceImpl(SysRoleUserMenuService sysRoleUserMenuService, SysMenuRepository sysMenuRepository) {
        this.sysRoleUserMenuService = sysRoleUserMenuService;
        this.sysMenuRepository = sysMenuRepository;
    }

    @Override
    public UserVisibleMenu getUserVisibleMenu() {
        var userVisibleMenu = new UserVisibleMenu();
        var username = CmsSysUserUtil.getCurrentUser().getUsername();
        //当前用户一级菜单
        var topMenus = this.sysRoleUserMenuService.getOneLevel(username);
        if (Detect.isNotNullOrEmpty(topMenus)) {
            //第一个一级菜单所有子菜单
            List<SysMenu> lefts = sysRoleUserMenuService.getUserMenusByParent(username, topMenus.get(0).getCode(), Byte.valueOf(IsValid.invalid.getValue()));
            //转换成 树状结构
            var leftMenus = MenuUtil.convertMenu2Tree(lefts);
            userVisibleMenu.setTopMenus(topMenus);
            userVisibleMenu.setLeftMenus(leftMenus);
        }
        return userVisibleMenu;
    }

    @Override
    public List<SysMenu> changeMode(String pCode) {
        var currentUser = CmsSysUserUtil.getCurrentUser();
        return this.sysRoleUserMenuService.getUserMenusByParent(currentUser.getUsername(), pCode, Byte.valueOf(IsValid.invalid.getValue()));
    }

    @Override
    public List<SysMenu> getAllMenus() {
        return this.sysMenuRepository.getAllMenus();
    }

    @Override
    public List<SysMenu> getAllMenusExcludeButton() {
        return this.sysMenuRepository.getAllMenusExcludeButton();
    }

    @Override
    public List<SysMenu> getMenuByCondition(SysMenu sysMenu) {
        //自定义匹配器规则，对于非string类型字段只支持精确匹配，有局限性
        var matcher = ExampleMatcher.matching()
                //全匹配，startWith左匹配
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("id", ExampleMatcher.GenericPropertyMatcher::exact);
        var example = Example.of(sysMenu, matcher);
        var orders = new ArrayList<Sort.Order>();
        //注意property 为 实体中的属性
        orders.add(new Sort.Order(Sort.Direction.ASC,"level"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"orderNum"));
        return this.sysMenuRepository.findAll(example, Sort.by(orders));
    }

    @Override
    public SysMenu selectById(Integer id) {
        return this.sysMenuRepository.findById(id).orElse(null);
    }

    @Override
    public SysMenu getByCode(String code) {
        return this.sysMenuRepository.findByCode(code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(SysMenu sysMenu) {
        checkCommonParam(sysMenu);
        var pCode = IsValid.fromValue(sysMenu.getIsTop()) == IsValid.valid ? "" : sysMenu.getPCode();
        var maxCode = this.sysMenuRepository.getMaxCodeByPCode(pCode);
        sysMenu.setCode(Detect.isNullOrEmpty(maxCode) ? sysMenu.getPCode() + "01" : Integer.parseInt(maxCode) + 1 + "");
        sysMenu.setLevel(Objects.isNull(sysMenu.getLevel()) ? Byte.valueOf("1") : Byte.valueOf(String.valueOf(sysMenu.getLevel() + 1)));
        sysMenu.setCreateUser(CmsSysUserUtil.getCurrentUser().getUsername());
        sysMenu.setUpdateUser(CmsSysUserUtil.getCurrentUser().getUsername());
        Date date = new Date();
        sysMenu.setCreateTime(date);
        sysMenu.setUpdateTime(date);
        this.sysMenuRepository.save(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenu sysMenu) {
        checkCommonParam(sysMenu);
        var menu = this.selectById(sysMenu.getId());
        sysMenu.setCreateTime(menu.getCreateTime());
        sysMenu.setCreateUser(menu.getCreateUser());
        sysMenu.setUpdateUser(CmsSysUserUtil.getCurrentUser().getUsername());
        sysMenu.setUpdateTime(new Date());
        this.sysMenuRepository.save(sysMenu);
    }

    private void checkCommonParam(SysMenu sysMenu) {
        new WebParamCheckUtils.Builder().menuName(sysMenu.getName()).menuLevel(sysMenu.getLevel()).menuType(sysMenu.getMenuType()).build().checkParam();
        if (IsValid.fromValue(sysMenu.getIsTop()) == IsValid.invalid && Detect.isNullOrEmpty(sysMenu.getPCode())) {
            log.error("The parent code of a non-top-level menu is null or empty");
            throw WebException.throwException(WebErrorCode.PARENT_CODE_EMPTY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Integer id) {
        //删除菜单角色中间表数据
        this.sysRoleUserMenuService.deleteRoleMenuByMenuId(id);
        this.sysMenuRepository.deleteById(id);
    }
}
