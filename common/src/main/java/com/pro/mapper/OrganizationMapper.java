package com.pro.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.pro.entity.Organization;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公司/机构表(sys_organization)数据Mapper
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
*/
@Mapper
public interface OrganizationMapper extends MPJBaseMapper<Organization> {

}
