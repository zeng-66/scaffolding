package com.pro.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.dao.ConfigDao;
import com.pro.entity.Config;
import com.pro.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 常量配置表服务接口实现
 *
 * @author zhangxiangyu
 * @since 2021-10-07 03:06:40
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConfigDao configDao;

    @Override
    public String getValueByKey(String key) {
        Config config = configDao.getValueByKey(key);
        return ObjectUtil.isNotNull(config)?config.getConfigValue(): StrUtil.EMPTY;
    }

    @Override
    public Config getByKey(String key) {
        return configDao.getValueByKey(key);
    }

    @Override
    public Page query(String key, int pageNo, int pageSize) {
        return configDao.query(key, pageNo, pageSize);
    }


}