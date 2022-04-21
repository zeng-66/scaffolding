package com.pro.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddConfigRequest {


    @ApiModelProperty("key")
    private String configKey;
    @ApiModelProperty("常量描述")
    private String configName;
    @ApiModelProperty("value")
    private String configValue;
}
