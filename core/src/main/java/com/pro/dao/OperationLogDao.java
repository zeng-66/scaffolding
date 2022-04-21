package com.pro.dao;

import lombok.extern.slf4j.Slf4j;
import com.pro.entity.OperationLog;
import com.pro.mapper.OperationLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 操作日志表(sys_operation_log)数据DAO
 *
 * @author zcl
 * @since 2022-04-21 15:50:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class OperationLogDao extends ServiceImpl<OperationLogMapper, OperationLog> {

}