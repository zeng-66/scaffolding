package com.pro.shiro;

import cn.hutool.core.util.StrUtil;
import com.pro.entity.BackAuthorities;
import com.pro.entity.BackRole;
import com.pro.entity.BackUser;
import com.pro.service.BackAuthoritiesService;
import com.pro.service.BackRoleService;
import com.pro.service.BackUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shiro认证和授权
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private BackUserService backUserService;
    @Autowired
    @Lazy
    private BackRoleService backRoleService;
    @Autowired
    @Lazy
    private BackAuthoritiesService backAuthoritiesService;

    public UserRealm() {
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        BackUser user = (BackUser) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 角色
        List<BackRole> userRoles = backRoleService.getRoleByUserId(user.getId());
        Set<String> roles = new HashSet<>();
        for (int i = 0; i < userRoles.size(); i++) {
            roles.add(String.valueOf(userRoles.get(i).getId()));
        }
        authorizationInfo.setRoles(roles);
        // 权限
        List<BackAuthorities> authorities = backAuthoritiesService.listByUserId(user.getId());
        Set<String> permissions = new HashSet<>();
        for (int i = 0; i < authorities.size(); i++) {
            String authority = authorities.get(i).getAuthority();
            if (StrUtil.isNotBlank(authority)) {
                permissions.add(authority);
            }
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        BackUser user = backUserService.getByUsername(username);
        if (user == null) {
            throw new UnknownAccountException(); // 账号不存在
        }
        if (user.getStatus() != 1) {
            throw new LockedAccountException();  // 账号被锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUserName()), getName());
        return authenticationInfo;
    }
}
