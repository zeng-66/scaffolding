package com.pro.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "权限列表请求模型")
public class AuthoritiesListRequest {

    @ApiModelProperty(value = "角色id，选传，传的话会返回这个角色是否有相应的权限", required = false)
    private Long roleId;

}
