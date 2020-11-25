package com.monkey.product.interceptor;

import com.alibaba.fastjson.JSON;
import com.monkey.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 搜索推荐 mdc 拦截器
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/23 14:59
 */
@Slf4j
@Component
public class MdcLogInterceptor implements HandlerInterceptor {

    private static final String DOMAIN_ID = "domain_id";

    @Value("${mdc.open-search-recommend:false}")
    private boolean isOpenSearchMdc;

    private List<String> domainId;

    public void setDomainId(List<String> domainId) {
        this.domainId = domainId;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        var body = HttpUtils.getRequestBody(request);
        var params = JSON.parseObject(body);
        if (isOpenSearchMdc && domainId.contains(params.getString(DOMAIN_ID))) {
            MDC.put(DOMAIN_ID, params.getString(DOMAIN_ID));
        }
        log.info("search recommend request params: {}", params);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        if (Objects.nonNull(MDC.get(DOMAIN_ID))) {
            MDC.remove(DOMAIN_ID);
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
