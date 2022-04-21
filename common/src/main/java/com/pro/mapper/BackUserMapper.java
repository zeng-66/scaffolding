package com.pro.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.pro.entity.BackUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 后台管理用户表(sys_back_user)数据Mapper
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
*/
@Mapper
public interface BackUserMapper extends MPJBaseMapper<BackUser> {

    void delById(@Param("id") Long id);
}
