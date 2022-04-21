package com.pro.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pro.entity.BackRole;

import java.util.List;

/**
 * 后台角色表服务接口
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
public interface BackRoleService {

    /**
     * 根据用户id查询角色列表
     *
     * @param userId
     * @return
     */
    List<BackRole> getRoleByUserId(Long userId);

    /**
     * 查询角色分页列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<BackRole> getPageList(long pageNo, long pageSize);

    /**
     * 添加角色
     *
     * @param roleName
     * @param roleName
     * @param status
     * @param remarks
     */
    void addRole(String orgCode, String roleName, Integer status, String remarks);

    /**
     * 修改角色
     *
     * @param id
     * @param roleName
     * @param status
     * @param remarks
     * @return
     */
    void editRole(Long id, String orgCode, String roleName, Integer status, String remarks);

    /**
     * 修改角色状态
     *
     * @param roleId
     * @param status
     * @return
     */
    void updateStatus(Long roleId, Integer status);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    void deleteRole(Long id);

    /**
     * 更新角色权限
     *
     * @param roleId
     * @param authIds
     * @return
     */
    void updateRoleAuth(Long roleId, String authIds);
}
