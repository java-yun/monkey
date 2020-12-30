package com.monkey.web.utils;

import com.monkey.common.utils.Detect;
import com.monkey.web.enums.MenuTypeEnum;
import com.monkey.web.exception.WebErrorCode;
import com.monkey.web.exception.WebException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * web 参数校验工具
 * @author jiangyun
 */
@Slf4j
public class WebParamCheckUtils {

    private static final int PWD_MIN_LENGTH = 6;
    private static final int PWD_MAX_LENGTH = 20;

    private final String username;
    private final boolean isCheckUsername;

    private final String cmsPwd;
    private final boolean isCheckCmsPwd;

    private final String menuName;
    private final boolean isCheckMenuName;

    private final Byte menuType;
    private final boolean isCheckMenuType;

    private final Byte menuLevel;
    private final boolean isCheckMenuLevel;


    public static class Builder {

        private String username;
        private boolean isCheckUsername;

        private String cmsPwd;
        private boolean isCheckCmsPwd;

        private String menuName;
        private boolean isCheckMenuName;

        private Byte menuType;
        private boolean isCheckMenuType;

        private Byte menuLevel;
        private boolean isCheckMenuLevel;


        public Builder(){

        }

        public Builder username(String username){
            this.username = username;
            this.isCheckUsername = true;
            return this;
        }

        public Builder cmsPwd(String cmsPwd){
            this.cmsPwd = cmsPwd;
            this.isCheckCmsPwd = true;
            return this;
        }

        public Builder menuName(String menuName){
            this.menuName = menuName;
            this.isCheckMenuName = true;
            return this;
        }

        public Builder menuType(Byte menuType){
            this.menuType = menuType;
            this.isCheckMenuType = true;
            return this;
        }

        public Builder menuLevel(Byte menuLevel){
            this.menuLevel = menuLevel;
            this.isCheckMenuLevel = true;
            return this;
        }


        public WebParamCheckUtils build() {
        	return new WebParamCheckUtils(this);
        }

    }

	public WebParamCheckUtils(Builder builder) {

		this.username = builder.username;
		this.isCheckUsername = builder.isCheckUsername;

        this.cmsPwd = builder.cmsPwd;
        this.isCheckCmsPwd = builder.isCheckCmsPwd;

        this.menuName = builder.menuName;
        this.isCheckMenuName = builder.isCheckMenuName;

        this.menuType = builder.menuType;
        this.isCheckMenuType = builder.isCheckMenuType;

        this.menuLevel = builder.menuLevel;
        this.isCheckMenuLevel= builder.isCheckMenuLevel;
    }


	public void checkParam(){
        if (isCheckUsername && Detect.isNullOrEmpty(username)) {
            log.error("username is empty or null");
            throw WebException.throwException(WebErrorCode.USER_NAME_IS_EMPTY);
        }
        if (isCheckCmsPwd && (Detect.isNullOrEmpty(cmsPwd) || cmsPwd.length() < PWD_MIN_LENGTH || cmsPwd.length() > PWD_MAX_LENGTH)) {
            log.error("password length error，pwd：{}", cmsPwd);
            throw WebException.throwException(WebErrorCode.PWD_LENGTH_ERROR);
        }
        if (isCheckMenuName && Detect.isNullOrEmpty(menuName)) {
            log.error("menu name is null or empty");
            throw WebException.throwException(WebErrorCode.MENU_NAME_IS_EMPTY);
        }
        if (isCheckMenuType && Objects.isNull(MenuTypeEnum.fromValue(menuType))) {
            log.error("illegal menu type");
            throw WebException.throwException(WebErrorCode.ILLEGAL_MENU_TYPE);
        }
        if (isCheckMenuLevel && Detect.isNullOrEmpty(menuLevel)) {
            log.error("menu level is null or empty");
            throw WebException.throwException(WebErrorCode.MENU_LEVEL_IS_EMPTY);
        }
    }

}
