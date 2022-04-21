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
 * 公司/机构表(sys_organization)实体类
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@ApiModel(description = "公司/机构表(sys_organization)实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_organization")
public class Organization extends SuperEntity implements Serializable {
    @ApiModelProperty("")
    private static final long serialVersionUID = 1L;

    /**
     * 公司/机构名称
     */
    @ApiModelProperty("公司/机构名称")
    private String orgName;
    /**
     * 公司/机构编码
     */
    @ApiModelProperty("公司/机构编码")
    private String orgCode;
    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String linkMan;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 状态：0-不启用 1-启用
     */
    @ApiModelProperty("状态：0-不启用 1-启用")
    private Integer status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}