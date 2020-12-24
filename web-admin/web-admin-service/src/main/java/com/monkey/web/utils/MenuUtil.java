package com.monkey.web.utils;

import com.monkey.web.bo.MenuTree;
import com.monkey.web.entity.SysMenu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单  工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 11:56
 */
@Slf4j
public class MenuUtil {

    /**
     * 将menu转换成树状
     * @param sysMenus sysMenus
     * @return List<MenuTree>
     */
    public static List<MenuTree> convertMenu2Tree(List<SysMenu> sysMenus){
        return doConvert(sysMenus, false);
    }

    /**
     * 转换成树状逻辑
     * @param sysMenus sysMenus
     * @param isContinue 父子关系是否连续  true 连续;   false 非连续
     * @return List<MenuTree>
     */
    private static List<MenuTree> doConvert(List<SysMenu> sysMenus, boolean isContinue){
        var beginTime = System.currentTimeMillis();
        if(sysMenus.isEmpty()){
            return null;
        }
        var menuTrees = new ArrayList<MenuTree>();
        sysMenus.forEach( sysMenu -> menuTrees.add(sysMenuToMenuTree(sysMenu)));
        var initTree = new ArrayList<MenuTree>();
        for(MenuTree menuTree : menuTrees){
            //由于menuTree是按level排序查出,所以只需要轮询level等于第一个元素的即可
            if(!isContinue && (menuTree.getLevel().intValue() != sysMenus.get(0).getLevel().intValue())){
                break;
            }
            initTree.add(getChild(menuTree,menuTrees));
        }
        var endTime = System.currentTimeMillis();
        log.info("将List转换成Tree形结构共用了【 {}ms 】", (endTime - beginTime));
        return initTree;
    }

    /**
     * 递归获取子类元素,并且设置到 children 中
     * @param menuTree menuTree
     * @param menuTrees menuTrees
     * @return MenuTree
     */
    private static MenuTree getChild(MenuTree menuTree, List<MenuTree> menuTrees){
        var children = menuTree.getChildren();
        for(MenuTree tree : menuTrees){
            if(menuTree.getCode().equals(tree.getPCode())){
                children.add(getChild(tree, menuTrees));
                menuTree.setChildren(children);
            }
        }
        return menuTree;
    }

    /**
     * 从A 对象属性拷贝到 B 对象
     * @param sysMenu sysMenu
     * @return MenuTree
     */
    private static MenuTree sysMenuToMenuTree(SysMenu sysMenu){
        var childTree = new MenuTree();
        childTree.setCode(sysMenu.getCode());
        childTree.setIcon(sysMenu.getIcon());
        childTree.setId(sysMenu.getId());
        childTree.setMenuType(sysMenu.getMenuType());
        childTree.setName(sysMenu.getName());
        childTree.setOrderNum(sysMenu.getOrderNum());
        childTree.setPCode(sysMenu.getPCode());
        childTree.setPermission(sysMenu.getPermission());
        childTree.setUrl(sysMenu.getUrl());
        childTree.setLevel(sysMenu.getLevel());
        childTree.setTitle(sysMenu.getName());
        childTree.setChecked(sysMenu.isChecked());
        childTree.setOpen(sysMenu.isOpen());
        return childTree;
    }
}
