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
 * 后台角色表(sys_back_role)实体类
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@ApiModel(description = "后台角色表(sys_back_role)实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_back_role")
public class BackRole extends SuperEntity implements Serializable {
    @ApiModelProperty("")
    private static final long serialVersionUID = 1L;

    /**
     * 公司/机构编码
     */
    @ApiModelProperty("公司/机构编码")
    private String orgCode;
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;
    /**
     * 状态：0-失效 1-有效
     */
    @ApiModelProperty("状态：0-失效 1-有效")
    private Integer status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remarks;

}