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

    private final String username;
    private final boolean isCheckUsername;


    public static class Builder {

        private String username;
        private boolean isCheckUsername = false;


        public Builder(){

        }

        public Builder timeStamp13(String timeStamp13){
            this.username = timeStamp13;
            this.isCheckUsername = true;
            return this;
        }


        public WebParamCheckUtils build() {
        	return new WebParamCheckUtils(this);
        }

    }

	public WebParamCheckUtils(Builder builder) {

		this.username = builder.username;
		this.isCheckUsername = builder.isCheckUsername;

	}


	public void checkParam(){
        if (isCheckUsername && Detect.isNullOrEmpty(username)) {
            log.error("用户名为空");
            throw WebException.throwException(WebErrorCode.USER_NAME_IS_EMPTY);
        }
    }

}
