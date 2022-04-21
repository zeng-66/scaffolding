package com.pro.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.pro.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请求日志(pro_operation_log)数据Mapper
 *
 * @author zhangxiangyu
 * @since 2022-02-02 17:11:20
 * @description 
*/
@Mapper
public interface OperationLogMapper extends MPJBaseMapper<OperationLog> {

}
