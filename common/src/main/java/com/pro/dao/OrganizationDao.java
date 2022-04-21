package com.pro.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.Organization;
import com.pro.mapper.OrganizationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公司/机构表(sys_organization)数据DAO
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@Repository
public class OrganizationDao extends ServiceImpl<OrganizationMapper, Organization> {

    public List<Organization> orgList() {
        return this.list(new LambdaQueryWrapper<Organization>().orderByDesc(Organization::getId));
    }

    public Page<Organization> orgPageList(String orgName, long pageNo, long pageSize) {
        LambdaQueryWrapper<Organization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(orgName),Organization::getOrgName, orgName);
        return page(new Page<>(pageNo, pageSize), queryWrapper);
    }

    public Organization getByOrgCode(String orgCode) {
        LambdaQueryWrapper<Organization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Organization::getOrgCode, orgCode);
        return getOne(queryWrapper, false);
    }
}