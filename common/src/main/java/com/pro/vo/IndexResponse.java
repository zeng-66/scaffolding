package com.pro.vo;

import com.pro.entity.BackUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "首页返回模型")
public class IndexResponse {

    @ApiModelProperty(value = "菜单树",required = true)
    private List<MenuTreeVo> menuTree;

    @ApiModelProperty(value = "登录用户信息",required = true)
    private BackUser backUser;

}
