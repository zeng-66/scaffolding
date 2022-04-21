package com.pro.sup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRequest {

    @ApiModelProperty("")
    protected int pageNo=1;
    @ApiModelProperty("")
    protected int pageSize=20;
}
