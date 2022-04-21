package com.pro.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "角色授权请求模型")
public class RoleAuthRequest {

    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;

    @ApiModelProperty(value = "权限id，多个用英文逗号隔开", required = true)
    private String authIds;

}
