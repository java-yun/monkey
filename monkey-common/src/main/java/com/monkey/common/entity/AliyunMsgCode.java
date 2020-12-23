package com.monkey.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云 短信发送实体
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/22 11:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliyunMsgCode {

    private String phone;

    private String code;

    private String template;

    private String accessKeyId;

    private String accessKeySecret;

    private String signature;

    private String outId = "yourOutId";
}
