package com.pro.service.impl;

import com.pro.dao.BackUserRoleDao;
import com.pro.entity.BackUserRole;
import com.pro.service.BackUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色关系表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BackUserRoleServiceImpl implements BackUserRoleService {
    private final BackUserRoleDao backUserRoleDao;

    @Override
    public List<BackUserRole> listByRoleId(Long roleId) {
        return backUserRoleDao.listByRoleId(roleId);
    }

    @Override
    public List<BackUserRole> listByUserId(Long userId) {
        return backUserRoleDao.listByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        backUserRoleDao.deleteByUserId(userId);
    }
}