package com.pro.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.BackRoleAuthorities;
import com.pro.mapper.BackRoleAuthoritiesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 后台角色权限关联表(sys_back_role_authorities)数据DAO
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@Repository
public class BackRoleAuthoritiesDao extends ServiceImpl<BackRoleAuthoritiesMapper, BackRoleAuthorities> {

    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<BackRoleAuthorities> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackRoleAuthorities::getRoleId, roleId);
        baseMapper.delete(queryWrapper);
    }

    public List<BackRoleAuthorities> listByRoles(List<Long> roleIds){
        if(CollectionUtils.isEmpty(roleIds)){
            return null;
        }
        LambdaQueryWrapper<BackRoleAuthorities> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(BackRoleAuthorities::getRoleId, roleIds);
        return baseMapper.selectList(wrapper);
    }

    public List<BackRoleAuthorities> listByAuthId(Long authorityId){
        LambdaQueryWrapper<BackRoleAuthorities> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BackRoleAuthorities::getAuthorityId, authorityId);
        return baseMapper.selectList(wrapper);
    }
}