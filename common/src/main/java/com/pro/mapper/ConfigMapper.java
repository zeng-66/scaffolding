package com.pro.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.pro.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 常量配置表(sys_config)数据Mapper
 *
 * @author zhangxiangyu
 * @since 2021-10-07 03:06:40
 * @description 
*/
@Mapper
public interface ConfigMapper extends MPJBaseMapper<Config> {



        @Select("SELECT config_value FROM `lht_config` WHERE config_key = \"AUTOMATIC_PAYMENT_DATE_SETTING\"")
        public String getConfigValue();
}
