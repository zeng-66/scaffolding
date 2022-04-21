package com.pro.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.entity.Config;

/**
 * 常量配置表服务接口
 *
 * @author zhangxiangyu
 * @since 2021-10-07 03:06:40
 * @description 
 */
public interface ConfigService {

    /**
     * 获取value
     * @param key
     * @return
     */
    String getValueByKey(String key);

    /**
     * 通过Key获取Config
     * @param key
     * @return
     */
    Config getByKey(String key);

    /**
     * 查询config
     * @param key
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page query(String key,int pageNo,int pageSize);


}
