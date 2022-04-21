package com.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pro.sup.SuperEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志(pro_operation_log)实体类
 *
 * @author zhangxiangyu
 * @since 2022-02-02 17:11:20
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pro_operation_log")
public class OperationLog extends SuperEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
    /**
     * ip
     */
    private String ip;

}