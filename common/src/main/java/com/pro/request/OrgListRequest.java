package com.pro.request;

import com.pro.sup.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: chengyunxiao
 * @Date: 2021/12/30/16:15
 * @Description:
 */
@Data
@ApiModel(description = "公司/机构分页列表请求模型")
public class OrgListRequest extends PageRequest {

    @ApiModelProperty(value = "公司/机构名称")
    private String orgName;
}
