package com.monkey.web.constants;

/**
 * 表 查询字段  常量
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/24 15:28
 */
public final class QueryColumnConstants {

    /**
     * cms用户表 查询字段
     */
    public static final String SYS_USER_SELECT = """
        select u.id, u.username, u.password, u.mobile, u.age, u.email, u.photo, u.real_name,
        u.del_flag, u.type, u.create_user, u.create_time, u.update_user, u.update_time""";

    /**
     * cms角色表 查询字段
     */
    public static final String SYS_ROLE_SELECT = """
        select r.id, r.code, r.p_code, r.role_name, r.description, r.create_user, r.create_time, r.update_user, r.update_time, r.remark""";

    /**
     * cms菜单表 查询字段
     */
    public static final String SYS_MENU_SELECT = """
        select m.id, m.name, m.code, m.p_code, m.level, m.url, m.order_num, m.icon,
        m.permission, m.menu_type, m.create_user, m.create_time, m.update_user, m.update_time""";


}
