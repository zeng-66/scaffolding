package com.pro.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "修改密码请求模型")
public class UpdatePswRequest {

    @NotNull(message = "请输入旧密码")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPsw;

    @NotNull(message = "请输入新密码")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPsw;

}
