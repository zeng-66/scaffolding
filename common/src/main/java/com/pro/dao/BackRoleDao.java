package com.pro.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.BackRole;
import com.pro.mapper.BackRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 后台角色表(sys_back_role)数据DAO
 *
 * @author zhangxiangyu
 * @description
 * @since 2022-01-25 00:14:51
 */
@Slf4j
@Repository
public class BackRoleDao extends ServiceImpl<BackRoleMapper, BackRole> {

    /**
     * 根据角色名称查询
     *
     * @param roleName
     * @return
     */
    public BackRole getByRoleName(String roleName) {
        LambdaQueryWrapper<BackRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackRole::getRoleName, roleName);
        return getOne(queryWrapper, false);
    }
}