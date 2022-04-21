package com.pro.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.entity.BackAuthorities;
import com.pro.mapper.BackAuthoritiesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 后台权限表(sys_back_authorities)数据DAO
 *
 * @author zhangxiangyu
 * @since 2022-01-25 00:14:51
 * @description 
 */
@Slf4j
@Repository
public class BackAuthoritiesDao extends ServiceImpl<BackAuthoritiesMapper, BackAuthorities> {

}