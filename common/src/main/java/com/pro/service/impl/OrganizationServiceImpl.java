package com.pro.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.dao.OrganizationDao;
import com.pro.entity.Organization;
import com.pro.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司/机构表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationDao organizationDao;

    @Override
    public List<Organization> orgList() {
        return organizationDao.orgList();
    }

    @Override
    public Page<Organization> orgPageList(String orgName, long pageNo, long pageSize) {
        return organizationDao.orgPageList(orgName, pageNo, pageSize);
    }

    @Override
    public Organization getByOrgCode(String orgCode) {
        return organizationDao.getByOrgCode(orgCode);
    }

    @Override
    public Organization getById(Long id) {
        return organizationDao.getById(id);
    }

    @Override
    public void removeById(Long id) {
        organizationDao.removeById(id);
    }
}