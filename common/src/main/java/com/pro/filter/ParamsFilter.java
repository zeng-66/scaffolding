package com.pro.filter;

import com.pro.interceptor.RequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",filterName = "paramsFilter")
public class ParamsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        if(requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            try {
                filterChain.doFilter(requestWrapper, servletResponse);
            }catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }
 
    @Override
    public void destroy() {

    }
}