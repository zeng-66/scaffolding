package com.pro.service.impl;

import com.pro.dao.OperationLogDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.pro.service.OperationLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志表服务接口实现
 *
 * @author zcl
 * @since 2022-04-21 15:50:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OperationLogServiceImpl implements OperationLogService {
    private final OperationLogDao operationLogDao;

}