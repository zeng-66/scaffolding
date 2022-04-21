package com.pro.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "状态修改请求模型")
public class StatusRequest extends IdRequest {

    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

}
