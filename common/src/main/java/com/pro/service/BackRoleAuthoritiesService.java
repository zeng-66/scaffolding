package com.pro.service;


import com.pro.entity.BackRoleAuthorities;

import java.util.List;

/**
 * 后台角色权限关联表服务接口
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
public interface BackRoleAuthoritiesService {

    /**
     * 根据角色id删除角色权限关系
     *
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据角色查询角色权限关系
     *
     * @param roleIds
     * @return
     */
    List<BackRoleAuthorities> listByRoles(List<Long> roleIds);

    /**
     * 根据权限id查询角色权限关系
     *
     * @param authorityId
     * @return
     */
    List<BackRoleAuthorities> listByAuthId(Long authorityId);
}
