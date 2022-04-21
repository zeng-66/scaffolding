package com.pro.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.entity.Organization;

import java.util.List;

/**
 * 公司/机构表服务接口
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
public interface OrganizationService {
    /**
     * 查询全部公司/机构
     *
     * @return
     */
    List<Organization> orgList();

    /**
     * 查询公司/机构分页列表
     *
     * @param orgName
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Organization> orgPageList(String orgName, long pageNo, long pageSize);

    /**
     * 根据公司/机构编码查询
     *
     * @param orgCode
     * @return
     */
    Organization getByOrgCode(String orgCode);
    Organization getById(Long id);
    void removeById(Long id);


}
