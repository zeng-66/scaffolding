package com.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pro.sup.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 常量配置表(sys_config)实体类
 *
 * @author zhangxiangyu
 * @since 2021-10-07 03:06:40
 * @description 
 */
@ApiModel(description = "常量配置表(sys_config)实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_config")
public class Config extends SuperEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @ApiModelProperty("key")
    private String configKey;
    /**
     * 常量描述
     */
    @ApiModelProperty("常量描述")
    private String configName;
    /**
     * value
     */
    @ApiModelProperty("value")
    private String configValue;

}