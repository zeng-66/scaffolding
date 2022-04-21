package com.pro.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.dao.BackRoleDao;
import com.pro.dao.BackUserRoleDao;
import com.pro.entity.BackRole;
import com.pro.entity.BackRoleAuthorities;
import com.pro.entity.BackUserRole;
import com.pro.exception.GlobalRuntimeException;
import com.pro.service.BackRoleAuthoritiesService;
import com.pro.service.BackRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台角色表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BackRoleServiceImpl implements BackRoleService {
    private final BackRoleDao backRoleDao;
    private final BackUserRoleDao backUserRoleDao;
    private final BackRoleAuthoritiesService backRoleAuthoritiesService;

    @Override
    public List<BackRole> getRoleByUserId(Long userId){
        //拼接sql都写在dao层
        LambdaQueryWrapper<BackUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUserRole::getUserId, userId);
        List<BackUserRole> userRoleList = backUserRoleDao.list(queryWrapper);
        List<Long> roleIds = new ArrayList<>();
        userRoleList.forEach(r -> {
            roleIds.add(r.getRoleId());
        });
        return backRoleDao.listByIds(roleIds);
    }

    @Override
    public IPage<BackRole> getPageList(long pageNo, long pageSize){
        return backRoleDao.page(new Page<>(pageNo, pageSize));
    }

    @Override
    public void addRole(String orgCode, String roleName, Integer status, String remarks){
        if(StrUtil.isBlank(roleName)){
            throw new GlobalRuntimeException("角色名称不能为空");
        }
        BackRole orginBackRole = backRoleDao.getByRoleName(roleName);
        if (orginBackRole != null){
            throw new GlobalRuntimeException("角色名重复");
        }
        BackRole backRole = new BackRole();
        backRole.setOrgCode(orgCode);
        backRole.setRoleName(roleName);
        backRole.setStatus(status == null ? 1 : status);
        backRole.setRemarks(remarks);
        backRole.insert();
    }

    @Override
    public void editRole(Long id, String orgCode, String roleName, Integer status, String remarks){
        if(id == null){
            throw new GlobalRuntimeException("id不能为空");
        }
        if(StrUtil.isBlank(roleName)){
            throw new GlobalRuntimeException("角色名称不能为空");
        }
        BackRole backRole = backRoleDao.getById(id);
        if(backRole == null){
            throw new GlobalRuntimeException("没有找到角色");
        }
        BackRole orginBackRole = backRoleDao.getByRoleName(roleName);
        if (orginBackRole != null && orginBackRole.getRoleName().equals(roleName) && orginBackRole.getId().longValue() != id.longValue()){
            throw new GlobalRuntimeException("角色名重复");
        }
        backRole.setOrgCode(orgCode);
        backRole.setRoleName(roleName);
        backRole.setStatus(status == null ? backRole.getStatus() : status);
        backRole.setRemarks(remarks);
        backRole.updateById();
    }

    @Override
    public void updateStatus(Long roleId, Integer status){
        if(roleId == null){
            throw new GlobalRuntimeException("角色id不能为空");
        }
        BackRole backRole = backRoleDao.getById(roleId);
        if(backRole == null){
            throw new GlobalRuntimeException("没有找到角色");
        }
        backRole.setStatus(status == null ? backRole.getStatus() : status);
        backRole.updateById();
    }

    @Override
    public void deleteRole(Long id){
        if(id == null){
            throw new GlobalRuntimeException("id不能为空");
        }
        List<BackUserRole> userRoleList = backUserRoleDao.listByRoleId(id);
        if(!CollectionUtils.isEmpty(userRoleList)){
            throw new GlobalRuntimeException("有用户拥有该角色，不能删除");
        }
        //删除角色
        backRoleDao.removeById(id);
        //删除角色权限关系
        backRoleAuthoritiesService.deleteByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleAuth(Long roleId, String authIds){
        if(roleId == null){
            throw new GlobalRuntimeException("角色id不能为空");
        }
        if(StrUtil.isBlank(authIds)){
            throw new GlobalRuntimeException("请选择权限");
        }
        String[] authIdsStr = authIds.split(",");
        if(authIdsStr == null || authIdsStr.length == 0){
            throw new GlobalRuntimeException("请选择权限");
        }
        //先删除原有的角色权限关系
        backRoleAuthoritiesService.deleteByRoleId(roleId);
        //查询角色
        BackRole backRole = backRoleDao.getById(roleId);
        if (backRole == null){
            throw new GlobalRuntimeException("没有找到角色");
        }
        //添加新的角色权限关系
        for(String authId : authIdsStr){
            BackRoleAuthorities roleAuthorities = new BackRoleAuthorities();
            roleAuthorities.setOrgCode(backRole.getOrgCode());
            roleAuthorities.setRoleId(roleId);
            roleAuthorities.setAuthorityId(authId == null ? null : Long.parseLong(authId));
            roleAuthorities.insert();
        }
    }
}