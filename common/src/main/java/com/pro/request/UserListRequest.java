package com.pro.request;

import com.pro.sup.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户列表请求模型")
public class UserListRequest extends PageRequest {

    @ApiModelProperty(value = "用户姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "公司/机构编码")
    private String orgCode;

}
