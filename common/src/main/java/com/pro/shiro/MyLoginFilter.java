package com.pro.shiro;

import com.alibaba.fastjson.JSON;
import com.pro.sup.ErrorCode;
import com.pro.sup.Result;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 自定义shiro过滤器
 */
public class MyLoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            servletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter out = servletResponse.getWriter();
            out.write(JSON.toJSONString(Result.fail(ErrorCode.NO_LOGIN)));
            out.flush();
            return false;

        }
        return true;
    }

    /**
     * 判断是不是ajax请求
     */
    private boolean isAjax(HttpServletRequest request) {
        String xHeader = request.getHeader("X-Requested-With");
        if (xHeader != null && xHeader.contains("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
