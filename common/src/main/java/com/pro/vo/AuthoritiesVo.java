package com.pro.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "后台管理权限返回模型")
public class AuthoritiesVo {

    @ApiModelProperty(value = "权限id",required = true)
    private Long id;

    @ApiModelProperty("公司/机构编码")
    private String orgCode;

    @ApiModelProperty(value = "权限名称",required = true)
    private String authorityName;

    @ApiModelProperty(value = "授权标识",required = true)
    private String authority;

    @ApiModelProperty(value = "菜单url",required = true)
    private String menuUrl;

    @ApiModelProperty(value = "父id",required = true)
    private Long parentId;

    @ApiModelProperty(value = "0菜单，1按钮",required = true)
    private Integer menuType;

    @ApiModelProperty(value = "排序号",required = true)
    private Integer sort;

    @ApiModelProperty(value = "菜单图标",required = true)
    private String menuIcon;

    @ApiModelProperty(value = "是否选中",required = true)
    private Integer checked;

    @ApiModelProperty(value = "子权限",required = true)
    private List<AuthoritiesVo> subMenus;

}
