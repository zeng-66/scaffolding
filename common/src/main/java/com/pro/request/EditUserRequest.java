package com.pro.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author： zcl
 * @date 2022/4/18 11:40
 * @description：
 */
@Data
public class EditUserRequest {

    @ApiModelProperty(value = "用户id", required = false)
    private Long id;

    @NotBlank(message = "公司/机构必须选择")
    @ApiModelProperty(value = "公司/机构编码")
    private String orgCode;

    @ApiModelProperty(value = "用户姓名", required = true)
    private String realName;

    @NotBlank(message = "角色必须选择")
    @ApiModelProperty(value = "角色id，多个角色用英文逗号隔开", required = true)
    private String roleId;
}
