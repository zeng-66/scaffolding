package com.pro.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pro.request.*;
import com.pro.service.BackUserService;
import com.pro.shiro.EndecryptUtil;
import com.pro.sup.Result;
import com.pro.vo.BackUserVo;
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

@Slf4j
@RestController
@Api(tags = "后台用户接口")
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {

    private final BackUserService backUserService;

    @RequiresPermissions("user:view")
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @PostMapping(value = "/userList")
    public Result<IPage<BackUserVo>> userList(@Valid @RequestBody UserListRequest request) {
        IPage<BackUserVo> backUserPage = backUserService.getUserList(request.getRealName(), request.getOrgCode(), request.getPageNo(), request.getPageSize());
        return Result.success(backUserPage);
    }

    @RequiresPermissions("user:add")
    @ApiOperation(value = "添加后台用户", notes = "添加后台用户")
    @PostMapping(value = "/addUser")
    public Result addUser(@Valid @RequestBody UserUpdateRequest request) {
        backUserService.addUser(request.getOrgCode(), request.getUserName(), request.getRealName(), request.getRoleId(), EndecryptUtil.encrytMd5("123456", request.getUserName(), 3));
        return Result.success();
    }

    @RequiresPermissions("user:edit")
    @ApiOperation(value = "修改后台用户", notes = "修改后台用户")
    @PostMapping(value = "/editUser")
    public Result editUser(@Valid @RequestBody EditUserRequest request) {
        backUserService.editUser(request.getId(), request.getOrgCode(),  request.getRealName(), request.getRoleId());
        return Result.success();
    }

    @RequiresPermissions("user:edit")
    @ApiOperation(value = "修改用户状态", notes = "修改用户状态")
    @PostMapping(value = "/updateUserStatus")
    public Result updateUserStatus(@Valid @RequestBody StatusRequest request) {
        backUserService.updateStatus(request.getId(), request.getStatus());
        return Result.success();
    }

    @RequiresPermissions("user:delete")
    @ApiOperation(value = "删除后台用户", notes = "删除后台用户")
    @PostMapping(value = "/deleteUser")
    public Result deleteUser(@Valid @RequestBody IdRequest request) {
        backUserService.deleteUser(request.getId());
        return Result.success();
    }


}
