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
 * 后台权限表(sys_back_authorities)实体类
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@ApiModel(description = "后台权限表(sys_back_authorities)实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_back_authorities")
public class BackAuthorities extends SuperEntity implements Serializable {
    @ApiModelProperty("")
    private static final long serialVersionUID = 1L;

    /**
     * 公司/机构编码
     */
    @ApiModelProperty("公司/机构编码")
    private String orgCode;
    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String authorityName;
    /**
     * 授权标识
     */
    @ApiModelProperty("授权标识")
    private String authority;
    /**
     * 菜单url
     */
    @ApiModelProperty("菜单url")
    private String menuUrl;
    /**
     * 父id
     */
    @ApiModelProperty("父id")
    private Long parentId;
    /**
     * 0菜单，1按钮
     */
    @ApiModelProperty("0菜单，1按钮")
    private Integer menuType;
    /**
     * 排序号
     */
    @ApiModelProperty("排序号")
    private Integer sort;
    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String menuIcon;

}