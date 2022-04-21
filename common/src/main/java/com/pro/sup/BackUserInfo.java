package com.pro.sup;

import com.pro.entity.BackUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Controller基类
 * @author cyx
 */
public class BackUserInfo {

    /**
     * 获取当前登录的user
     */
    public BackUser getLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Object object = subject.getPrincipal();
            if (object != null) {
                return (BackUser) object;
            }
        }
        return null;
    }

    /**
     * 获取当前登录的userId
     */
    public Long getLoginUserId() {
        BackUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getId();
    }

    /**
     * 获取当前登录的username
     */
    public String getLoginUserName() {
        BackUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUserName();
    }

    /**
     * 获取当前登录用户的公司编码
     * @return
     */
    public String getOrgCode(){
        BackUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getOrgCode();
    }



}
