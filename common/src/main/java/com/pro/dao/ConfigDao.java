package com.pro.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.Config;
import com.pro.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 常量配置表(sys_config)数据DAO
 *
 * @author zhangxiangyu
 * @since 2021-10-07 03:06:40
 * @description 
 */
@Slf4j
@Repository
public class ConfigDao extends ServiceImpl<ConfigMapper, Config> {

    public Config getValueByKey(String key){
        LambdaQueryWrapper<Config> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Config::getConfigKey,key);
        wrapper.eq(Config::getIsDelete,Boolean.FALSE);
        return getOne(wrapper,false);
    }

    public Page query(String key, int pageNo, int pageSize){
        LambdaQueryWrapper<Config> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(key),Config::getConfigKey,key);
        return page(new Page<>(pageNo,pageSize),wrapper);
    }
}