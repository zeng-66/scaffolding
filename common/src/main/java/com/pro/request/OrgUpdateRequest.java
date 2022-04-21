package com.pro.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: chengyunxiao
 * @Date: 2021/12/30/16:31
 * @Description:
 */
@Data
@ApiModel("添加/修改公司请求模型")
public class OrgUpdateRequest {

    @ApiModelProperty(value = "id")
    private Long id;

    @NotBlank(message = "公司/机构名称不能为空")
    @ApiModelProperty(value = "公司/机构名称", required = true)
    private String orgName;

    @NotBlank(message = "公司/机构编码不能为空")
    @ApiModelProperty(value = "公司/机构编码", required = true)
    private String orgCode;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "备注")
    private String remark;
}
