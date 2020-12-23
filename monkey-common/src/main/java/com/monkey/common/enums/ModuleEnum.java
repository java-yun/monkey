package com.monkey.common.enums;

/**
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 13:58
 */
public enum ModuleEnum {

    /**
     * 系统  模块
     */
    CMS_WEB("monkey:web", "cms web模块"),
    PRODUCT("monkey:product", "商品模块"),
    ;

    private final String module;
    private final String desc;

    ModuleEnum(String module, String desc) {
        this.module = module;
        this.desc = desc;
    }

    public static ModuleEnum fromValue(String type) {
        for (ModuleEnum moduleEnum : ModuleEnum.values()) {
            if (moduleEnum.getModule().equals(type)) {
                return moduleEnum;
            }
        }
        return null;
    }

    public static String getDescByName(String type) {
        for(ModuleEnum moduleEnum : ModuleEnum.values()) {
            if (moduleEnum.getModule().equals(type)) {
                return moduleEnum.getDesc();
            }
        }
        return "";
    }

    public String getModule() {
        return module;
    }

    public String getDesc() {
        return desc;
    }
}
