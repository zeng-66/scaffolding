package com.pro.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "角色添加/修改请求模型")
public class RoleUpdateRequest {

    @ApiModelProperty(value = "角色id", required = false)
    private Long id;

    @ApiModelProperty(value = "公司/机构编码")
    private String orgCode;

    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @ApiModelProperty(value = "状态：0-失效 1-有效", required = true)
    private Integer status;

    @ApiModelProperty(value = "备注", required = false)
    private String remarks;
}
