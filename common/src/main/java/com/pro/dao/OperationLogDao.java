package com.pro.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.OperationLog;
import com.pro.mapper.OperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 请求日志(pro_operation_log)数据DAO
 *
 * @author zhangxiangyu
 * @since 2022-02-02 17:11:20
 * @description 
 */
@Slf4j
@Repository
public class OperationLogDao extends ServiceImpl<OperationLogMapper, OperationLog> {

}