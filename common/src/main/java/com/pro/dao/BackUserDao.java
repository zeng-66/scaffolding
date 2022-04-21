package com.pro.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.BackUser;
import com.pro.mapper.BackUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 后台管理用户表(sys_back_user)数据DAO
 *
 * @author zhangxiangyu
 * @description
 * @since 2022-01-25 00:14:51
 */
@Slf4j
@Repository
public class BackUserDao extends ServiceImpl<BackUserMapper, BackUser> {

    /**
     * 根据用户名查询
     *
     * @param userName
     * @return
     */
    public BackUser getByUserName(String userName) {
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUser::getUserName, userName);
        return getOne(queryWrapper, false);
    }

    /**
     * 删除用户
     * @param id
     */
    public void delById(Long id) {
        baseMapper.delById(id);
    }
}