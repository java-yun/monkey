package com.monkey.web.handler;

import com.monkey.common.code.BizCode;
import com.monkey.common.exception.SystemException;
import com.monkey.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常拦截器
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/13 14:01
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response<String> handle(Exception e) {
        if (e instanceof SystemException exception) {
            return Response.error(exception.getCode(), exception.getMessage());
        } else {
            log.error("[系统异常]{}" + e.getMessage(), e);
            return Response.error(BizCode.ERROR);
        }
    }
}
