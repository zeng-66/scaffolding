package com.pro.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "登录请求模型")
public class LoginRequest {

    @NotBlank(message = "请输入登录名")
    @ApiModelProperty(value = "登录名", required = true)
    private String userName;

    @NotBlank(message = "请输入密码")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
