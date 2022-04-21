package com.pro.service.impl;

import com.pro.dao.OperationLogDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.pro.service.OperationLogService;
import org.springframework.stereotype.Service;

/**
 * 请求日志服务接口实现
 *
 * @author zhangxiangyu
 * @since 2022-02-02 17:11:20
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OperationLogServiceImpl implements OperationLogService {
    private final OperationLogDao operationLogDao;

}