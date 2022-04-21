package com.pro.service.impl;


import com.pro.dao.UserDao;
import com.pro.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户表服务接口实现
 *
 * @author zcl
 * @since 2022-04-21 15:41:42
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

}