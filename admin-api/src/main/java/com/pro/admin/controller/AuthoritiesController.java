package com.pro.admin.controller;

import com.pro.entity.BackAuthorities;
import com.pro.request.AuthoritiesListRequest;
import com.pro.request.IdRequest;
import com.pro.service.BackAuthoritiesService;
import com.pro.sup.Result;
import com.pro.vo.AuthoritiesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "后台权限接口")
@RequestMapping("/admin/menu")
@RequiredArgsConstructor
public class AuthoritiesController {

    private final BackAuthoritiesService backAuthoritiesService;

    /**
     * 查询所有权限
     *
     * @param request
     * @return
     */
    @RequiresPermissions("authorities:view")
    @ApiOperation(value = "查询权限列表", notes = "查询权限列表")
    @PostMapping(value = "/authoritiesList")
    public Result<List<AuthoritiesVo>> authoritiesList(@Valid @RequestBody AuthoritiesListRequest request) {
        List<AuthoritiesVo> authorities = backAuthoritiesService.getListTree(request.getRoleId(), -1L);
        return Result.success(authorities);
    }

    /**
     * 添加权限
     *
     * @param authorities
     * @return
     */
    @RequiresPermissions("authorities:add")
    @ApiOperation(value = "添加权限", notes = "添加权限")
    @PostMapping(value = "/addAuthorities")
    public Result addAuthorities(@Valid @RequestBody BackAuthorities authorities) {
        authorities.insert();
        return Result.success();
    }

    /**
     * 修改权限
     *
     * @param authorities
     * @return
     */
    @RequiresPermissions("authorities:edit")
    @ApiOperation(value = "修改权限", notes = "修改权限")
    @PostMapping(value = "/editAuthorities")
    public Result editAuthorities(@Valid @RequestBody BackAuthorities authorities) {
        if (authorities.getId() == null) {
            return Result.fail("id不能为空");
        }
        if (backAuthoritiesService.getById(authorities.getId()) == null) {
            return Result.fail("没有找到权限");
        }
        authorities.updateById();
        return Result.success();
    }

    /**
     * 删除权限
     *
     * @param request
     * @return
     */
    @RequiresPermissions("authorities:delete")
    @ApiOperation(value = "删除权限", notes = "删除权限")
    @PostMapping(value = "/deleteAuthorities")
    public Result deleteAuthorities(@Valid @RequestBody IdRequest request) {
        backAuthoritiesService.deleteAuth(request.getId());
        return Result.success();
    }


}
