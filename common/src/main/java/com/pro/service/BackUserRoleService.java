package com.pro.service;


import com.pro.entity.BackUserRole;

import java.util.List;

/**
 * 用户角色关系表服务接口
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
public interface BackUserRoleService {


    /**
     * 根据角色id查询用户角色关系
     *
     * @param roleId
     * @return
     */
    List<BackUserRole> listByRoleId(Long roleId);

    /**
     * 根据用户id查询用户角色关系
     *
     * @param userId
     * @return
     */
    List<BackUserRole> listByUserId(Long userId);

    /**
     * 根据用户id删除用户角色关系
     *
     * @param userId
     */
    void deleteByUserId(Long userId);
}
