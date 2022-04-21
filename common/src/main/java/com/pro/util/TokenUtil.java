package com.pro.util;

import cn.hutool.crypto.SecureUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenUtil {


    private final String TOKEN_FORMAT= ("MOUSHI_MEMBER_TOKEN_");
    private final String ADMIN_TOKEN_FORMAT=("MOUSHI_USER_TOKEN_");


    public String getRedisTokenKey(String token){
        return TOKEN_FORMAT+token;
    }
    public String getRedisTokenAdminKey(String token){
        return ADMIN_TOKEN_FORMAT+token;
    }

    public String createToken(Long userId){
        String token=  SecureUtil.sha256(System.currentTimeMillis()+""+userId);
        return token ;
    }

    public String createAdminToken(Long userId){
        String token=  SecureUtil.md5(System.currentTimeMillis()+""+userId);
        return token ;
    }

}
