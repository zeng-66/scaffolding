package com.pro.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdRequest {

    @ApiModelProperty("Id")
    @NotNull(message = "Id不能为空")
    private Long id;

}
