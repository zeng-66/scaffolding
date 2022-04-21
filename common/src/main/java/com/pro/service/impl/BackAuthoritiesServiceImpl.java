package com.pro.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.pro.dao.BackAuthoritiesDao;
import com.pro.entity.BackAuthorities;
import com.pro.entity.BackRoleAuthorities;
import com.pro.entity.BackUserRole;
import com.pro.exception.GlobalRuntimeException;
import com.pro.service.BackAuthoritiesService;
import com.pro.service.BackRoleAuthoritiesService;
import com.pro.service.BackUserRoleService;
import com.pro.vo.AuthoritiesVo;
import com.pro.vo.MenuTreeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 后台权限表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BackAuthoritiesServiceImpl implements BackAuthoritiesService {
    private final BackAuthoritiesDao backAuthoritiesDao;
    private final BackUserRoleService backUserRoleService;
    private final BackRoleAuthoritiesService backRoleAuthoritiesService;

    @Override
    public List<MenuTreeVo> getMenuTree(List<BackAuthorities> authorities, Long parentId){
        if(CollectionUtils.isEmpty(authorities) || parentId == null){
            //尽可能不要返回null 如果是列表展示前端渲染直接可以用空集合 就不需要兼容null
            return null;
//            return Collections.emptyList();
        }
        List<MenuTreeVo> list = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++) {
            BackAuthorities temp = authorities.get(i);
            if (parentId.equals(temp.getParentId())) {
                MenuTreeVo menuTree = new MenuTreeVo();
                menuTree.setMenuName(temp.getAuthorityName());
                menuTree.setMenuIcon(temp.getMenuIcon());
                menuTree.setMenuUrl(temp.getMenuUrl());
                menuTree.setAuthority(temp.getAuthority());
                menuTree.setMenuType(temp.getMenuType());
                menuTree.setSubMenus(getMenuTree(authorities, authorities.get(i).getId()));
                list.add(menuTree);
            }
        }
        return list;
    }

    @Override
    public List<AuthoritiesVo> getListTree(Long roleId, Long parentId){
        List<BackAuthorities> allAuthoritiesList = backAuthoritiesDao.list();
        List<BackAuthorities> roleAuths = listByRoleId(roleId);
        Set<Long> roleAuthIdSet = roleAuths.stream().map(BackAuthorities::getId).collect(Collectors.toSet());

        return getMenus(roleAuthIdSet, parentId, allAuthoritiesList);
    }

    /**
     *
     * @param roleAuthIdSet         此角色拥有的权限Id列表
     * @param parentId              父级Id
     * @param allAuthoritiesList    所有权限
     * @return list
     */
    private List<AuthoritiesVo> getMenus(Set<Long> roleAuthIdSet, Long parentId, List<BackAuthorities> allAuthoritiesList) {
        List<BackAuthorities> authoritiesList = allAuthoritiesList.stream().filter(auth -> Objects.equals(auth.getParentId(), parentId)).collect(Collectors.toList());
        List<AuthoritiesVo> authoritiesVOList = new ArrayList<>();
        if (CollUtil.isEmpty(allAuthoritiesList)) {
            return authoritiesVOList;
        }
        for(BackAuthorities one : authoritiesList){
            AuthoritiesVo authoritiesVO = new AuthoritiesVo();
            authoritiesVO.setId(one.getId());
            authoritiesVO.setOrgCode(one.getOrgCode());
            authoritiesVO.setAuthorityName(one.getAuthorityName());
            authoritiesVO.setAuthority(one.getAuthority());
            authoritiesVO.setMenuUrl(one.getMenuUrl());
            authoritiesVO.setParentId(one.getParentId());
            authoritiesVO.setMenuType(one.getMenuType());
            authoritiesVO.setSort(one.getSort());
            authoritiesVO.setMenuIcon(one.getMenuIcon());
            authoritiesVO.setChecked(0);
            if (roleAuthIdSet.contains(one.getId())) {
                authoritiesVO.setChecked(1);
            }
            authoritiesVO.setSubMenus(getMenus(roleAuthIdSet, one.getId(), allAuthoritiesList));
            authoritiesVOList.add(authoritiesVO);
        }
        return authoritiesVOList;
    }

    @Override
    public List<BackAuthorities> listByUserId(Long userId){
        List<BackUserRole> userRoleList = backUserRoleService.listByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)){
            return null;
        }
        //明确集合长度时候最好指定长度  避免多次扩容带来的效率损失
        List<Long> roleIds = new ArrayList<>(userRoleList.size());
        userRoleList.forEach(r -> {
            roleIds.add(r.getRoleId());
        });
        List<BackRoleAuthorities> backRoleAuthoritiesList = backRoleAuthoritiesService.listByRoles(roleIds);
        if(CollectionUtils.isEmpty(backRoleAuthoritiesList)){
            return null;
        }
        List<Long> authIds = new ArrayList<>(backRoleAuthoritiesList.size());
        backRoleAuthoritiesList.forEach(a -> {
            authIds.add(a.getAuthorityId());
        });
        return backAuthoritiesDao.listByIds(authIds);
    }

    @Override
    public List<BackAuthorities> listByRoleId(Long roleId){
        if(roleId == null){
            return new ArrayList<BackAuthorities>();
        }
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        List<BackRoleAuthorities> backRoleAuthoritiesList = backRoleAuthoritiesService.listByRoles(roleIds);
        if(CollectionUtils.isEmpty(backRoleAuthoritiesList)){
            return new ArrayList<BackAuthorities>();
        }
        List<Long> authIds = new ArrayList<>(backRoleAuthoritiesList.size());
        backRoleAuthoritiesList.forEach(a -> {
            authIds.add(a.getAuthorityId());
        });
        return backAuthoritiesDao.listByIds(authIds);
    }

    @Override
    public void deleteAuth(Long authorityId){
        if(authorityId == null){
            throw new GlobalRuntimeException("参数不正确");
        }
        List<BackRoleAuthorities> roleAuthoritiesList = backRoleAuthoritiesService.listByAuthId(authorityId);
        if(!CollectionUtils.isEmpty(roleAuthoritiesList)){
            throw new GlobalRuntimeException("有角色拥有该权限，不能删除");
        }
        backAuthoritiesDao.removeById(authorityId);
    }

    @Override
    public BackAuthorities getById(Long id) {
        return backAuthoritiesDao.getById(id);
    }
}