package com.pro.request;

import com.pro.sup.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryConfigRequest extends PageRequest {

    @ApiModelProperty("key")
    private String configKey;

}
