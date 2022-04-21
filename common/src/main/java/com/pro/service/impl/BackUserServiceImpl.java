package com.pro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.dao.BackRoleDao;
import com.pro.dao.BackUserDao;
import com.pro.entity.BackRole;
import com.pro.entity.BackUser;
import com.pro.entity.BackUserRole;
import com.pro.exception.GlobalRuntimeException;
import com.pro.service.BackUserRoleService;
import com.pro.service.BackUserService;
import com.pro.vo.BackUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台管理用户表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BackUserServiceImpl implements BackUserService {
    private final BackUserDao backUserDao;
    private final BackRoleDao backRoleDao;
    private final BackUserRoleService backUserRoleService;

    @Override
    public BackUser getByUsername(String userName){
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BackUser::getUserName, userName);
        return backUserDao.getOne(queryWrapper, false);
    }

    @Override
    public void updateLastLoginTime(String userName){
        BackUser backUser = getByUsername(userName);
        if(backUser != null){
            backUser.setLastLoginTime(new Date());
            backUser.updateById();
        }
    }

    @Override
    public void updatePsw(Long userId, String userName, String newPsw){
        BackUser user = backUserDao.getById(userId);
        if(user != null){
            //user.setPassword(EndecryptUtil.encrytMd5(newPsw, userName, 3));
            user.setPassword(newPsw);
            user.updateById();
        }
    }

    @Override
    public Page<BackUserVo> getUserList(String realName, String orgCode, long pageNo, long pageSize){
        LambdaQueryWrapper<BackUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(orgCode)){
            queryWrapper.eq(BackUser::getOrgCode, orgCode);
        }
        if (StrUtil.isNotBlank(realName)){
            queryWrapper.like(BackUser::getRealName, realName);
        }
        IPage<BackUser> backUserIPage = backUserDao.page(new Page<>(pageNo, pageSize), queryWrapper);
        Page<BackUserVo> backUserPage = new Page<>();
        if(backUserIPage != null && !CollectionUtils.isEmpty(backUserIPage.getRecords())){
            backUserPage.setCurrent(backUserIPage.getCurrent());
            backUserPage.setPages(backUserIPage.getPages());
            backUserPage.setSize(backUserIPage.getSize());
            backUserPage.setTotal(backUserIPage.getTotal());
            List<BackUserVo> backUserVOList = new ArrayList<>();
            for(BackUser backUser : backUserIPage.getRecords()){
                BackUserVo backUserVO = new BackUserVo();
                BeanUtil.copyProperties(backUser, backUserVO);
                backUserVO.setUserId(backUser.getId());
                List<BackUserRole> userRoleList = backUserRoleService.listByUserId(backUserVO.getUserId());
                if(!CollectionUtils.isEmpty(userRoleList)){
                    StringBuilder sb = new StringBuilder();
                    for(BackUserRole userRole : userRoleList){
                        sb.append(userRole.getRoleId());
                        sb.append(",");
                    }
                    backUserVO.setRoleIds(sb.toString().substring(0, sb.length()-1));
                }
                backUserVOList.add(backUserVO);
            }
            backUserPage.setRecords(backUserVOList);
        }
        return backUserPage;
    }

    @Override
    public void addUser(String orgCode, String userName, String realName, String roleId, String password){
        if(StrUtil.isBlank(userName)){
            throw new GlobalRuntimeException("用户名必须填写");
        }
        if(StrUtil.isBlank(roleId)){
            throw new GlobalRuntimeException("角色必须选择");
        }
        BackUser orginBackUser = backUserDao.getByUserName(userName);
        if (orginBackUser != null){
            throw new GlobalRuntimeException("用户名重复");
        }
        //添加用户
        BackUser backUser = new BackUser();
        backUser.setOrgCode(orgCode);
        backUser.setUserName(userName);
        backUser.setRealName(realName);
        //backUser.setPassword(EndecryptUtil.encrytMd5("123456", userName, 3));
        backUser.setPassword(password);
        backUser.setStatus(1);
        backUser.insert();

        //添加用户角色关系
        String roleStr = addUserRole(backUser.getId(), roleId, orgCode);

        //更新用户表角色字段
        backUser.setRoles(roleStr);
        backUser.updateById();
    }

    @Override
    @Transactional
    public void editUser(Long userId, String orgCode, String realName, String roleId){
        if(userId == null){
            throw new GlobalRuntimeException("id不能为空");
        }
        if(StrUtil.isBlank(roleId)){
            throw new GlobalRuntimeException("角色必须选择");
        }
        //更新用户信息
        BackUser backUser = backUserDao.getById(userId);
        if(backUser == null){
            throw new GlobalRuntimeException("没有找到用户");
        }
        backUser.setOrgCode(orgCode);
        backUser.setRealName(realName);
        backUser.updateById();
        //删除原用户角色关系
        backUserRoleService.deleteByUserId(userId);
        //新增用户角色关系
        String roleStr = addUserRole(backUser.getId(), roleId, orgCode);
        //更新用户表角色字段
        backUser.setRoles(roleStr);
        backUser.updateById();
    }

    @Override
    public void updateStatus(Long userId, Integer status){
        if(userId == null){
            throw new GlobalRuntimeException("用户id不能为空");
        }
        BackUser backUser = backUserDao.getById(userId);
        if(backUser == null){
            throw new GlobalRuntimeException("没有找到用户");
        }
        if ("admin".equals(backUser.getUserName())){
            throw new GlobalRuntimeException("超级管理员不能修改");
        }
        backUser.setStatus(status == null ? backUser.getStatus() : status);
        backUser.updateById();
    }

    /**
     * 添加用户角色关系
     * @param userId
     * @param roleId
     * @return
     */
    private String addUserRole(Long userId, String roleId, String orgCode){
        String[] roleIdArry = roleId.split(",");
        StringBuffer sb = new StringBuffer();
        if(roleIdArry != null && roleIdArry.length > 0){
            for(String role : roleIdArry){
                BackRole backRole = backRoleDao.getById(role);
                if(backRole != null){
                    sb.append(backRole.getRoleName());
                    sb.append("，");
                    BackUserRole userRole = new BackUserRole();
                    userRole.setOrgCode(orgCode);
                    userRole.setRoleId(role == null ? null : Long.parseLong(role));
                    userRole.setUserId(userId);
                    userRole.insert();
                }
            }
        }
        String roleStr = sb.toString();
        return StrUtil.isBlank(roleStr) ? null : roleStr.substring(0, sb.length() - 1);
    }

    @Override
    public void deleteUser(Long id){
        if(id == null){
            throw new GlobalRuntimeException("id不能为空");
        }
        BackUser backUser = backUserDao.getById(id);
        if (backUser == null){
            throw new GlobalRuntimeException("用户不存在");
        }
        if ("admin".equals(backUser.getUserName())){
            throw new GlobalRuntimeException("超级管理员不能删除");
        }
        //删除用户
       //backUserDao.removeById(id);
        backUserDao.delById(id);
        //删除用户角色关系
        backUserRoleService.deleteByUserId(id);
    }
}