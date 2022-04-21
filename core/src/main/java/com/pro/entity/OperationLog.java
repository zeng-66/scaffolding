package com.pro.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pro.sup.SuperEntity;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 操作日志表(sys_operation_log)实体类
 *
 * @author zcl
 * @since 2022-04-21 15:50:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_operation_log")
public class OperationLog extends SuperEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ip
     */
    private String ip;
    /**
     * 接口路径
     */
    private String requestUrl;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 操作人Id
     */
    private Long userId;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 返回结果
     */
    private String requestResult;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 执行时间毫秒
     */
    private Long runTime;


}