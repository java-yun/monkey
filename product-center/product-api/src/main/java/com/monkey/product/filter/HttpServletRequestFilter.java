package com.monkey.product.filter;

import com.monkey.common.utils.BodyReaderHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * request wrapper filter
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/11/23 16:27
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "httpServletRequestFilter")
public class HttpServletRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("HttpServletRequestFilter init...................");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        ServletRequest wrapperRequest = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        filterChain.doFilter(wrapperRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
