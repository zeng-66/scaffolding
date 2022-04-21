package com.pro.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "后台管理用户返回模型")
public class BackUserVo {

    @ApiModelProperty(value = "用户id",required = true)
    private Long userId;

    @ApiModelProperty(value = "用户登录名",required = true)
    private String userName;

    @ApiModelProperty(value = "用户姓名",required = true)
    private String realName;

    @ApiModelProperty("公司/机构编码")
    private String orgCode;

    @ApiModelProperty(value = "最近一次登录时间",required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastLoginTime;

    @ApiModelProperty(value = "最近一次登录IP",required = true)
    private String lastLoginIp;

    @ApiModelProperty(value = "启用状态:0-禁用，1-启用",required = true)
    private Integer status;

    @ApiModelProperty(value = "角色id",required = true)
    private String roleIds;

    @ApiModelProperty(value = "角色",required = true)
    private String roles;

    @ApiModelProperty(value = "创建时间",required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "修改时间",required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyDate;

}
