package com.pro.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.BackUserRole;
import com.pro.mapper.BackUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关系表(sys_back_user_role)数据DAO
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@Repository
public class BackUserRoleDao extends ServiceImpl<BackUserRoleMapper, BackUserRole> {


    public List<BackUserRole> listByRoleId(Long roleId) {
        LambdaQueryWrapper<BackUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUserRole::getRoleId, roleId);
        return list(queryWrapper);
    }

    public List<BackUserRole> listByUserId(Long userId) {
        LambdaQueryWrapper<BackUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUserRole::getUserId, userId);
        return list(queryWrapper);
    }

    public void deleteByUserId(Long userId) {
        LambdaQueryWrapper<BackUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUserRole::getUserId, userId);
        baseMapper.delete(queryWrapper);
    }
}