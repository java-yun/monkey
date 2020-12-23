package com.monkey.common.service.impl;

import com.monkey.common.code.BizCode;
import com.monkey.common.constants.AliyunConstants;
import com.monkey.common.constants.MsgCodeConstants;
import com.monkey.common.entity.AliyunMsgCode;
import com.monkey.common.enums.MsgCodeRedisKeyEnum;
import com.monkey.common.exception.SystemException;
import com.monkey.common.repository.RedisRepository;
import com.monkey.common.service.MsgCodeService;
import com.monkey.common.utils.AliyunMsgUtils;
import com.monkey.common.utils.Detect;
import com.monkey.common.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 短信验证码发送 service impl
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 10:33
 */
@Slf4j
@Component
public class MsgCodeServiceImpl implements MsgCodeService {

    private final RedisRepository redisRepository;

    public MsgCodeServiceImpl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void sendAliyunMessage(String phoneNum, String module, String bizType) {
        //发送次数校验
        this.validateSendTimes(phoneNum);
        //redis 获取阿里云短信配置
        var accessKeyId = this.redisRepository.getString(AliyunConstants.MSG_ACCESS_KEY_ID_REDIS_KEY);
        var accessKeySecret = this.redisRepository.getString(AliyunConstants.MSG_ACCESS_KEY_SECRET_REDIS_KEY);
        var signature = this.redisRepository.getString(AliyunConstants.MSG_SIGNATURE_REDIS_KEY);
        var template = this.redisRepository.getString(AliyunConstants.MSG_TEMPLATE_REDIS_KEY);
        //生成验证码
        String code = GeneratorUtils.generate6Length();
        var aliyunMsgCode =  AliyunMsgCode.builder().code(code).phone(phoneNum).accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret).signature(signature).template(template).build();
        AliyunMsgUtils.sendSms(aliyunMsgCode);
        //保存验证码到redis
        log.info("手机号:" + phoneNum + " 手机验证码:" + code);
        this.redisRepository.setString(this.getMsgCodeRedisKey(phoneNum, module, bizType), code, MsgCodeConstants.EXPIRE_TIME);
    }

    @Override
    public void checkMsg(String phoneNum, String smsCode, String module, String bizType) {
        String code = this.redisRepository.getString(this.getMsgCodeRedisKey(phoneNum, module, bizType));
        if (Detect.isNotNullOrEmpty(code)) {
            log.info("验证码已过期");
            throw SystemException.throwException(BizCode.MSG_CODE_EXPIRED);
        }
        if (!code.equals(smsCode)) {
            log.info("验证码错误，传入验证码：{}，redis验证码：{}", smsCode, code);
            throw SystemException.throwException(BizCode.MSG_CODE_ERROR);
        }
        log.info("传入的验证码：{}，redis中的验证码：{}", smsCode, code);
        this.redisRepository.remove(this.getMsgCodeRedisKey(phoneNum, module, bizType));
    }

    private void validateSendTimes(String phoneNum) {
        // 查一天
        if (Objects.isNull(this.redisRepository.getString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_D.getKey() + phoneNum))) {
            this.redisRepository.setString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_D.getKey() + phoneNum, "1", MsgCodeConstants.EXPIRE_TIME_DAY_LIMIT);
        } else {
            String count = this.redisRepository.getString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_D.getKey() + phoneNum);
            if (Long.parseLong(count) >= MsgCodeConstants.SEND_TIMES_DAY) {
                throw SystemException.throwException(BizCode.MSG_OVER_DAY_TIMES);
            }
            String total = String.valueOf(Long.parseLong(count) + 1);
            this.redisRepository.setString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_D.getKey() + phoneNum, total, this.redisRepository.getExpire(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_D.getKey() + phoneNum));
        }

        // 查一小时
        if (Objects.isNull(this.redisRepository.getString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_H.getKey() + phoneNum))) {
            this.redisRepository.setString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_H.getKey() + phoneNum, "1", MsgCodeConstants.EXPIRE_TIME_HOUR_LIMIT);
        } else {
            String count = this.redisRepository.getString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_H.getKey() + phoneNum);
            if (Long.parseLong(count) >= MsgCodeConstants.SEND_TIMES_HOUR) {
                throw SystemException.throwException(BizCode.MSG_OVER_HOUR_TIMES);
            }
            String total = String.valueOf(Long.parseLong(count) + 1);
            this.redisRepository.setString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_H.getKey() + phoneNum, total, this.redisRepository.getExpire(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_H.getKey() + phoneNum));
        }

        // 查一分钟
        if (Objects.isNull(this.redisRepository.getString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_M.getKey() + phoneNum))) {
            this.redisRepository.setString(MsgCodeRedisKeyEnum.MESSAGE_CODE_TIME_M.getKey() + phoneNum, "1", MsgCodeConstants.EXPIRE_TIME_MINUTE_LIMIT);
        } else {
            throw SystemException.throwException(BizCode.MSG_OVER_MINUTE_TIMES);
        }
    }

    private String getMsgCodeRedisKey(String phoneNum, String module, String bizType) {
        return module + ":" + MsgCodeRedisKeyEnum.MSG_CODE.getKey() + phoneNum + ":" + bizType;
    }
}
