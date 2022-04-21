package com.pro.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class IdsRequest {

    @ApiModelProperty("Ids")
    @NotNull(message = "Id不能为空")
    private List<Long> ids;

}
