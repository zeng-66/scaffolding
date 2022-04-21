package com.pro.service.impl;

import com.pro.dao.BackRoleAuthoritiesDao;
import com.pro.entity.BackRoleAuthorities;
import com.pro.service.BackRoleAuthoritiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台角色权限关联表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BackRoleAuthoritiesServiceImpl implements BackRoleAuthoritiesService {
    private final BackRoleAuthoritiesDao backRoleAuthoritiesDao;

    @Override
    public void deleteByRoleId(Long roleId) {
        backRoleAuthoritiesDao.deleteByRoleId(roleId);
    }

    @Override
    public List<BackRoleAuthorities> listByRoles(List<Long> roleIds){
        return backRoleAuthoritiesDao.listByRoles(roleIds);
    }

    @Override
    public List<BackRoleAuthorities> listByAuthId(Long authorityId){
        return backRoleAuthoritiesDao.listByAuthId(authorityId);
    }
}