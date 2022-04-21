package com.pro.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.User;
import com.pro.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户表(jd_user)数据DAO
 *
 * @author zcl
 * @since 2022-04-21 15:41:42
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {

}