package com.pro.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "菜单树返回模型")
public class MenuTreeVo {

    @ApiModelProperty(value = "菜单名称",required = true)
    private String menuName;

    @ApiModelProperty(value = "菜单图标",required = true)
    private String menuIcon;

    @ApiModelProperty(value = "菜单链接",required = true)
    private String menuUrl;

    @ApiModelProperty(value = "授权标识",required = true)
    private String authority;

    @ApiModelProperty(value = "0-菜单，1-按钮",required = true)
    private Integer menuType;

    @ApiModelProperty(value = "子菜单列表",required = true)
    private List<MenuTreeVo> subMenus;

}
