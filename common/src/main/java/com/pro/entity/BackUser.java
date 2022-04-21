package com.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pro.annotation.DictMapping;
import com.pro.sup.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台管理用户表(sys_back_user)实体类
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@ApiModel(description = "后台管理用户表(sys_back_user)实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_back_user")
public class BackUser extends SuperEntity implements Serializable {
    @ApiModelProperty("")
    private static final long serialVersionUID = 1L;

    /**
     * 公司/机构编码
     */
    @ApiModelProperty("公司/机构编码")
    private String orgCode;
    /**
     * 用户登录名
     */
    @ApiModelProperty("用户登录名")
    private String userName;
    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    @DictMapping
    private String realName;
    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String headImage;
    /**
     * 最近一次登录时间
     */
    @ApiModelProperty("最近一次登录时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /**
     * 最近一次登录IP
     */
    @ApiModelProperty("最近一次登录IP")
    private String lastLoginIp;
    /**
     * 启用状态:0-禁用，1-启用
     */
    @ApiModelProperty("启用状态:0-禁用，1-启用")
    private Integer status;
    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private String roles;

}