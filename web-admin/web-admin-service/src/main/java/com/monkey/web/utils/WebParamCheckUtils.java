package com.monkey.web.utils;

import com.monkey.common.utils.Detect;
import com.monkey.web.exception.WebErrorCode;
import com.monkey.web.exception.WebException;
import lombok.extern.slf4j.Slf4j;

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


    public static class Builder {

        private String username;
        private boolean isCheckUsername;

        private String cmsPwd;
        private boolean isCheckCmsPwd;


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


        public WebParamCheckUtils build() {
        	return new WebParamCheckUtils(this);
        }

    }

	public WebParamCheckUtils(Builder builder) {

		this.username = builder.username;
		this.isCheckUsername = builder.isCheckUsername;

        this.cmsPwd = builder.cmsPwd;
        this.isCheckCmsPwd = builder.isCheckCmsPwd;
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
    }

}
