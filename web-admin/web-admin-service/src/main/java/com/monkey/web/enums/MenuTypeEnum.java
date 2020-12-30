package com.monkey.web.enums;

/**
 * 菜单类型枚举
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/29 9:56
 */
public enum MenuTypeEnum {

    /**
     * 菜单类型
     */
    MENU(Byte.valueOf("0"), "菜单"),
    BUTTON(Byte.valueOf("1"), "按钮"),
    ;

    private final Byte type;
    private final String name;

    MenuTypeEnum(Byte type, String name) {
        this.type = type;
        this.name = name;
    }

    public static MenuTypeEnum fromValue(Byte type) {
        for (MenuTypeEnum typeEnum: MenuTypeEnum.values()) {
            if (typeEnum.type.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public Byte getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
