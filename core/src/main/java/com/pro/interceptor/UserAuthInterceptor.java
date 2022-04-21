package com.pro.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.pro.annotation.UserLogin;

import com.pro.dao.UserDao;
import com.pro.entity.OperationLog;
import com.pro.entity.User;
import com.pro.exception.GlobalRuntimeException;
import com.pro.sup.ErrorCode;
import com.pro.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangxiangyu
 * @date 2021年3月25日
 */
@Component
@Slf4j
public class UserAuthInterceptor implements HandlerInterceptor {
    @Autowired
    UserDao userDao;

    @Autowired
    RedisTemplate redisTemplate;

    private String params ;
    private Long timeCount ;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        timeCount = System.currentTimeMillis();
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        params = new RequestWrapper(httpServletRequest).getBody();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLogin.class)) {
            UserLogin userLoginToken = method.getAnnotation(UserLogin.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (StrUtil.isBlank(token)) {
                    throw new GlobalRuntimeException(ErrorCode.NO_LOGIN);
                }
                String userId = (String)redisTemplate.opsForValue().get(TokenUtil.getRedisTokenKey(token));
                if (StrUtil.isBlank(userId)) {
                    throw new GlobalRuntimeException(ErrorCode.NO_LOGIN);
                }
                User user = userDao.getById(userId);
                if (user == null) {
                    throw new GlobalRuntimeException(ErrorCode.NO_LOGIN);
                }
                if (!user.getStatus()){
                    throw new GlobalRuntimeException(ErrorCode.USER_FROZEN);
                }
                UserPool.setUser(user);
                redisTemplate.opsForValue().set(TokenUtil.getRedisTokenKey(token),String.valueOf(user.getId()),10l, TimeUnit.DAYS);
                return true;
            }else {
                // 执行认证
                if (StrUtil.isNotBlank(token)) {
                    String userId = (String)redisTemplate.opsForValue().get(TokenUtil.getRedisTokenKey(token));
                    if (StrUtil.isBlank(userId)) {
                        throw new GlobalRuntimeException(ErrorCode.NO_LOGIN);
                    }
                    User user = userDao.getById(userId);
                    if (user == null) {
                        throw new GlobalRuntimeException(ErrorCode.NO_LOGIN);
                    }
                    if (!user.getStatus()){
                        throw new GlobalRuntimeException(ErrorCode.USER_FROZEN);
                    }
                    UserPool.setUser(user);
                    redisTemplate.opsForValue().set(TokenUtil.getRedisTokenKey(token),String.valueOf(user.getId()),10l, TimeUnit.DAYS);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        try {
            OperationLog operationLog = new OperationLog();
            operationLog.setOperationTime(new Date());
            operationLog.setRequestMethod(httpServletRequest.getMethod());
            if(StrUtil.equals(operationLog.getRequestMethod(),"GET")){
                operationLog.setRequestParams(JSON.toJSONString(httpServletRequest.getParameterMap()));
            }else{
                operationLog.setRequestParams(params);
            }
            operationLog.setRequestUrl(httpServletRequest.getRequestURI());
            operationLog.setUserId(UserPool.getUser()!=null?UserPool.getUser().getId():null);
            operationLog.setRunTime(System.currentTimeMillis()-timeCount);
            operationLog.setIp(ServletUtil.getClientIP(httpServletRequest));
            operationLog.insert();
        }catch (Exception e1){
            log.warn("save operation log fail");
        }
        UserPool.clear();
    }










}