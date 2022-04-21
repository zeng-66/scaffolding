package com.pro.sup;

/**
 * @author： zcl
 * @date 2022/3/14 17:48
 * @description：Redis前缀
 */
public class RedisPrefix{


    /**
     * 关闭超时订单
     */
    public final static String TIMEOUT_ORDER = "TIMEOUT_ORDER_";

    /**
     * 尝试获取验证码的并发锁前缀（一分钟一次）
     */
    public final static String GET_VERIFY_CODE_PREFIX = "GET_VERIFY_CODE_";

    /**
     * app用户注册、登录账号获取验证码
     */
    public final static String USER_REGISTRATION_OR_LOGIN_CODE = "USER_REGISTRATION_OR_LOGIN_CODE_";

    /**
     * app换绑手机号
     */
    public final static String USER_CHANGE_AND_BIND_MOBILE_PHONE_CODE = "USER_CHANGE_AND_BIND_MOBILE_PHONE_CODE_";

    /**
     * app用户登入TOKEN_KEY
     */
    public final static String USER_LOGIN_TOKEN_KEY = "USER_LOGIN_TOKEN_KEY_";

    /**
     * app解绑微信号发送验证码
     */
    public final static String USER_UNBOUND_MICRO_SIGNAL = "USER_UNBOUND_MICRO_SIGNAL_";


}
