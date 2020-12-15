package com.monkey.product.aop;

import com.monkey.common.constants.CommonConstants;
import com.monkey.common.response.Response;
import com.monkey.common.utils.GeneratorUtils;
import com.monkey.product.exception.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * rest 接口日志 aop
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/3 11:18
 */
@Slf4j
@Aspect
@Component
public class RestLogAspect {

    @Value("${log.mdc.open:false}")
    private boolean isOpenMdc;

    @Pointcut(value = "@annotation(com.monkey.product.annotation.RestLog)")
    public void pointCut() {}

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint point) {
        long startTimeMillis = System.currentTimeMillis();
        if (isOpenMdc) {
            MDC.put(CommonConstants.REQUEST_ID, CommonConstants.REQUEST_ID_PREFIX + GeneratorUtils.getUUID());
        }
        Response<?> result = null;
        try {
            result = (Response<?>) point.proceed(point.getArgs());
        } catch (Throwable throwable) {
            //必须抛出异常，否则会导致GlobalExceptionHandler无法捕获业务异常
            if (throwable instanceof ProductException e) {
                throw ProductException.throwException(e.getCode(), e.getMessage());
            } else {
                throw new RuntimeException(throwable.getMessage());
            }
        } finally {
            long endTimeMillis = System.currentTimeMillis();
            String methodName = point.getSignature().getName();
            log.info("method name: {}, execute time: {} \nargs: {}, \nresult: {}", methodName, endTimeMillis-startTimeMillis, point.getArgs(), result);
            MDC.clear();
        }
        return result;
    }


}
