package com.pro.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pro.sup.SuperEntity;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户表(jd_user)实体类
 *
 * @author zcl
 * @since 2022-04-21 15:41:42
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("jd_user")
public class User extends SuperEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户unionid
     */
    private String unionid;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 微信号
     */
    private String wxCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 性别：0-不限/未知  1-男 2-女
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
    /**
     * 企业ID
     */
    private Long corporateId;
    /**
     * 状态：0-禁用 1-启用
     */
    private Boolean status;


}