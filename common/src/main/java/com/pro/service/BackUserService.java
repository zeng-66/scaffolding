package com.pro.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.entity.BackUser;
import com.pro.vo.BackUserVo;

/**
 * 后台管理用户表服务接口
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
public interface BackUserService {

    /**
     * 根据用户名称查询用户
     *
     * @param userName
     * @return
     */
    BackUser getByUsername(String userName);

    /**
     * 更新用户的最后登录时间
     *
     * @param userName
     */
    void updateLastLoginTime(String userName);

    /**
     * 修改密码
     *
     * @param userId
     * @param userName
     * @param newPsw
     */
    void updatePsw(Long userId, String userName, String newPsw);

    /**
     * 分页查询用户列表
     *
     * @param realName
     * @param orgCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BackUserVo> getUserList(String realName, String orgCode, long pageNo, long pageSize);

    /**
     * 添加用户
     *
     * @param orgCode
     * @param userName
     * @param realName
     * @param roleId
     * @param password
     * @return
     */
    void addUser(String orgCode, String userName, String realName, String roleId, String password);

    /**
     * 修改用户
     *
     * @param userId
     * @param orgCode
     * @param realName
     * @param roleId
     * @return
     */
    void editUser(Long userId, String orgCode, String realName, String roleId);

    /**
     * 修改用户状态
     *
     * @param userId
     * @param status
     * @return
     */
    void updateStatus(Long userId, Integer status);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void deleteUser(Long id);
}
