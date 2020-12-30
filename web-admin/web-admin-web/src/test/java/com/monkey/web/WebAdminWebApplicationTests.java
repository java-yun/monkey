package com.monkey.web;

import com.alibaba.fastjson.JSON;
import com.monkey.web.entity.SysMenu;
import com.monkey.web.service.sys.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebAdminWebApplicationTests {

    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void jpaDynamicQuery() {
        var sysMenu = new SysMenu();
        sysMenu.setName("管理");
        var menus = this.sysMenuService.getMenuByCondition(sysMenu);
        System.out.println("menu list:" + JSON.toJSONString(menus));
    }

}
