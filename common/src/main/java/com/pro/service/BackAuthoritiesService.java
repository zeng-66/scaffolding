package com.pro.service;


import com.pro.entity.BackAuthorities;
import com.pro.vo.AuthoritiesVo;
import com.pro.vo.MenuTreeVo;

import java.util.List;

/**
 * 后台权限表服务接口
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
public interface BackAuthoritiesService {
    /**
     * 查询菜单树
     *
     * @param authorities
     * @param parentId
     * @return
     */
    List<MenuTreeVo> getMenuTree(List<BackAuthorities> authorities, Long parentId);

    /**
     * 查询权限列表树
     *
     * @param roleId
     * @param parentId
     * @return
     */
    List<AuthoritiesVo> getListTree(Long roleId, Long parentId);

    /**
     * 根据用户id查询用户权限
     *
     * @param userId
     * @return
     */
    List<BackAuthorities> listByUserId(Long userId);

    /**
     * 根据角色id查询权限
     *
     * @param roleId
     * @return
     */
    List<BackAuthorities> listByRoleId(Long roleId);

    /**
     * 删除权限
     *
     * @param authorityId
     * @return
     */
    void deleteAuth(Long authorityId);
    BackAuthorities getById(Long id);

}
