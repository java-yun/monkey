//package com.monkey.product.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.monkey.common.utils.GeneratorUtils;
//import com.monkey.common.utils.HttpUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Objects;
//
///**
// * 搜索推荐 mdc 拦截器
// * @author jiangyun
// * @version 0.0.1
// * @date 2020/11/23 14:59
// */
//@Slf4j
//@Component
//public class MdcLogInterceptor implements HandlerInterceptor {
//
//    private static final String SEARCH = "search";
//
//    @Value("${log.mdc.open:false}")
//    private boolean isOpenMdc;
//
//    @Override
//    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
//        var body = HttpUtils.getRequestBody(request);
//        var params = JSON.parseObject(body);
//        MDC.put(SEARCH, GeneratorUtils.getUUID());
//        log.info("search recommend request params: {}", params);
//        return HandlerInterceptor.super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
//        if (Objects.nonNull(MDC.get(SEARCH))) {
//            MDC.remove(SEARCH);
//        }
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
//}
