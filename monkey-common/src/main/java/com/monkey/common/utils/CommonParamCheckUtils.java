package com.monkey.common.utils;

import com.monkey.common.code.BizCode;
import com.monkey.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用参数校验工具类
 * @author jiangyun
 */
@Slf4j
public class CommonParamCheckUtils {

    /**
     * 32位 uuid生成的字符串校验
     */
    private static final String UUID32_REGEXP = "[a-zA-Z0-9]{32}";

    /**
     * 6位数字短信验证码正则表达式(默认)
     */
    private static final String MSG_CODE_REGEX = "[0-9]{6}";

    /**
     * 毫秒级时间戳正则表达式
     */
    private static final String MS_TIMESTAMP_REGEXP = "[0-9]{13}";

    /**
     * 身份证号正则表达式(默认)
     */
    private static final String ID_CARD_NO_REGEX = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))" +
            "(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)" ;

    /**
     * 电话号码正则表达式(默认)
     */
    private static final String PHONE_CHECK_REGEXP = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$" ;

    /**
     * IP的正则表达式
     */
    private static final String IPV4_REGEXP = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))" ;



    /**
     * 时间戳（13位）
     */
    private final String timeStamp13;
    private final boolean isCheckTimeStamp13;

    /**
     * 32位uuid检查
     */
    private final String uuid32;
    private final boolean isCheckUuid32;

    /**
     * 有效性
     */
    private final String isValid;
    private final boolean isCheckIsValid;

    /**
     * 参数非空判断
     */
    private final Object isNull;
    private final boolean isCheckIsNull;

    /**
     * ipv4
     */
    private final String ipv4;
    private final boolean isCheckIpv4;

    /**
     * 手机号
     */
    private final String phoneNum;
    private final boolean isCheckPhoneNum;

    /**
     * 性别
     */
    private final String sex;
    private final boolean isCheckSex;

    /**
     * 6位数短信验证码
     */
    private final String msgCode;
    private final boolean isCheckMsgCode;



    public static class Builder {

        private String timeStamp13;
        private boolean isCheckTimeStamp13 = false;

        private String uuid32;
        private boolean isCheckUuid32 = false;

        private String isValid;
        private boolean isCheckIsValid = false;

        private Object isNull;
        private boolean isCheckIsNull = false;

        private String isEmpty;
        private boolean isCheckEmpty = false;

        private String ipv4;
        private boolean isCheckIpv4 = false;

        private String phoneNum;
        private boolean isCheckPhoneNum = false;

        private String sex;
        private boolean isCheckSex = false;

        private String msgCode;
        private boolean isCheckMsgCode = false;

        public Builder(){

        }

        public Builder timeStamp13(String timeStamp13){
            this.timeStamp13 = timeStamp13;
            this.isCheckTimeStamp13 = true;
            return this;
        }

        public Builder uuid32(String uuid32){
            this.uuid32 = uuid32;
            this.isCheckUuid32 = true;
            return this;
        }

        public Builder isValid(String isValid){
            this.isValid = isValid;
            this.isCheckIsValid = true;
            return this;
        }

        public Builder isNull(Object isNull){
            this.isNull = isNull;
            this.isCheckIsNull = true;
            return this;
        }

        public Builder isEmpty(String isEmpty){
            this.isEmpty = isEmpty;
            this.isCheckEmpty = true;
            return this;
        }

        public Builder ipv4(String ipv4){
            this.ipv4 = ipv4;
            this.isCheckIpv4 = true;
            return this;
        }

        public Builder phoneNum(String phoneNum){
            this.phoneNum = phoneNum;
            this.isCheckPhoneNum = true;
            return this;
        }

        public Builder sex(String sex){
            this.sex = sex;
            this.isCheckSex = true;
            return this;
        }

        public Builder msgCode(String msgCode){
            this.msgCode = msgCode;
            this.isCheckMsgCode = true;
            return this;
        }

        public CommonParamCheckUtils build() {
        	return new CommonParamCheckUtils(this);
        }

    }

	public CommonParamCheckUtils(Builder builder) {
		this.ipv4 = builder.ipv4;
		this.isCheckIpv4 = builder.isCheckIpv4;

		this.timeStamp13 = builder.timeStamp13;
		this.isCheckTimeStamp13 = builder.isCheckTimeStamp13;

		this.uuid32 = builder.uuid32;
		this.isCheckUuid32 = builder.isCheckUuid32;

		this.isValid = builder.isValid;
		this.isCheckIsValid = builder.isCheckIsValid;

		this.isNull = builder.isNull;
		this.isCheckIsNull = builder.isCheckIsNull;

		this.phoneNum = builder.phoneNum;
		this.isCheckPhoneNum = builder.isCheckPhoneNum;

		this.sex = builder.sex;
		this.isCheckSex = builder.isCheckSex;

		this.msgCode = builder.msgCode;
		this.isCheckMsgCode = builder.isCheckMsgCode;

	}


	public void checkParam(){
        if (isCheckPhoneNum && !phoneNum.matches(PHONE_CHECK_REGEXP)) {
            log.error("手机号格式不正确，phoneNum：{}", phoneNum);
            throw SystemException.throwException(BizCode.ILLEGAL_PHONE_NUM);
        }
    }

}
