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
 * 用户角色关系表(sys_back_user_role)实体类
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@ApiModel(description = "用户角色关系表(sys_back_user_role)实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_back_user_role")
public class BackUserRole extends SuperEntity implements Serializable {
    @ApiModelProperty("")
    private static final long serialVersionUID = 1L;

    /**
     * 公司/机构编码
     */
    @ApiModelProperty("公司/机构编码")
    private String orgCode;
    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

}