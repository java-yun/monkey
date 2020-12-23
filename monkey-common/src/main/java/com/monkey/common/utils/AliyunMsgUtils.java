package com.monkey.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.monkey.common.code.BizCode;
import com.monkey.common.constants.AliyunConstants;
import com.monkey.common.entity.AliyunMsgCode;
import com.monkey.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 阿里云 短信工具类
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 11:49
 */
@Slf4j
public class AliyunMsgUtils {

    public static void sendSms(AliyunMsgCode aliyunMsgCode) {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", AliyunConstants.SMS_CODE_EXPIRE);
        System.setProperty("sun.net.client.defaultReadTimeout", AliyunConstants.SMS_CODE_EXPIRE);

        //初始化acsClient,暂不支持region化
        var profile = DefaultProfile.getProfile("cn-hangzhou", aliyunMsgCode.getAccessKeyId(), aliyunMsgCode.getAccessKeySecret());
        SendSmsResponse sendSmsResponse;
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliyunConstants.PRODUCT, AliyunConstants.DOMAIN);
            var acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(aliyunMsgCode.getPhone());
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(aliyunMsgCode.getSignature());
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(aliyunMsgCode.getTemplate());
            if(Detect.isNotNullOrEmpty(aliyunMsgCode.getCode())){
                request.setTemplateParam("{\"code\":\"" + aliyunMsgCode.getCode() + "\"}");
            }
            request.setOutId(aliyunMsgCode.getOutId());
            //hint 此处可能会抛出异常，注意catch
            sendSmsResponse = acsClient.getAcsResponse(request);
            log.info("阿里云短信发送响应结果response：{}", JSONObject.toJSONString(sendSmsResponse));
        } catch (ClientException e) {
            log.error("阿里云短信发送异常", e);
            throw SystemException.throwException(BizCode.MSG_SEND_ERROR);
        }
        if (Objects.isNull(sendSmsResponse) || !AliyunConstants.MSG_SEND_SUC_CODE.equals(sendSmsResponse.getCode())) {
            log.error("阿里云短信发送响应异常，response： {}", sendSmsResponse);
            throw SystemException.throwException(BizCode.MSG_SEND_ERROR);
        }
    }

    public static void main(String[] args) {
        var aliyunMsgCode = AliyunMsgCode.builder().phone("18955310615").code("123456").accessKeyId("LTAIrCQSLbqFlTOX").accessKeySecret("61lM5fhgrllST4ZJw3kCNP8OCmvYgB")
                .signature("奕玩网络").template("SMS_170840971").build();
        sendSms(aliyunMsgCode);
    }
}
