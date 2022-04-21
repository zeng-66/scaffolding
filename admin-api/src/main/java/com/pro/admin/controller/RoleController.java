package com.pro.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pro.entity.BackRole;
import com.pro.request.IdRequest;
import com.pro.request.RoleAuthRequest;
import com.pro.request.RoleUpdateRequest;
import com.pro.request.StatusRequest;
import com.pro.service.BackRoleService;
import com.pro.sup.PageRequest;
import com.pro.sup.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "后台角色接口")
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private BackRoleService backRoleService;

    @RequiresPermissions("role:view")
    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @PostMapping(value = "/roleList")
    public Result<IPage<BackRole>> roleList(@Valid @RequestBody PageRequest request) {
        IPage<BackRole> backRoleIPage = backRoleService.getPageList(request.getPageNo(), request.getPageSize());
        return Result.success(backRoleIPage);
    }

    @RequiresPermissions("role:add")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping(value = "/addRole")
    public Result addRole(@Valid @RequestBody RoleUpdateRequest request) {
        backRoleService.addRole(request.getOrgCode(), request.getRoleName(), request.getStatus(), request.getRemarks());
        return Result.success();
    }

    @RequiresPermissions("role:edit")
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PostMapping(value = "/editRole")
    public Result editRole(@Valid @RequestBody RoleUpdateRequest request) {
        backRoleService.editRole(request.getId(), request.getOrgCode(), request.getRoleName(), request.getStatus(), request.getRemarks());
        return Result.success();
    }

    @RequiresPermissions("role:edit")
    @ApiOperation(value = "修改角色状态", notes = "修改角色状态")
    @PostMapping(value = "/updateRoleStatus")
    public Result updateRoleStatus(@Valid @RequestBody StatusRequest request) {
        backRoleService.updateStatus(request.getId(), request.getStatus());
        return Result.success();
    }

    @RequiresPermissions("role:delete")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping(value = "/deleteRole")
    public Result deleteRole(@Valid @RequestBody IdRequest request) {
        backRoleService.deleteRole(request.getId());
        return Result.success();
    }

    @RequiresPermissions("role:auth")
    @ApiOperation(value = "修改角色权限", notes = "修改角色权限")
    @PostMapping(value = "/updateRoleAuth")
    public Result updateRoleAuth(@Valid @RequestBody RoleAuthRequest request) {
        backRoleService.updateRoleAuth(request.getRoleId(), request.getAuthIds());
        return Result.success();
    }

}
