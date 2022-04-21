package com.pro.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.pro.entity.Config;
import com.pro.request.AddConfigRequest;
import com.pro.request.QueryConfigRequest;
import com.pro.service.ConfigService;
import com.pro.sup.PageResult;
import com.pro.sup.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 常量配置表服务控制器
 *
 * @author zhangxiangyu
 * @description
 * @since 2021-10-07 03:06:40
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/config")
@Api(tags = "常量配置管理模块")
public class ConfigAdminController {
    private final ConfigService configService;

    @RequiresPermissions("config:add")
    @ApiOperation(value = "添加常量", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result add(@Valid @RequestBody AddConfigRequest request) {
        if (ObjectUtil.isNotNull(configService.getByKey(request.getConfigKey()))) {
            return Result.fail("key已存在");
        }
        Config config = BeanUtil.toBean(request, Config.class);
        config.insert();
        return Result.success();
    }

    @RequiresPermissions("config:edit")
    @ApiOperation(value = "编辑常量", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result edit(@Valid @RequestBody AddConfigRequest request){
        Config config = configService.getByKey(request.getConfigKey());
        if (ObjectUtil.isNull(config)) {
            return Result.fail("key不存在");
        }
        BeanUtil.copyProperties(request, config);
        config.updateById();
        return Result.success();
    }

    @RequiresPermissions("config:view")
    @ApiOperation(value = "常量列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResult query(@Valid @RequestBody QueryConfigRequest request){
        return PageResult.success(configService.query(request.getConfigKey(), request.getPageNo(), request.getPageSize()));
    }



}