package com.pro.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.entity.Organization;
import com.pro.enums.StatusEnum;
import com.pro.exception.GlobalRuntimeException;
import com.pro.request.IdRequest;
import com.pro.request.OrgListRequest;
import com.pro.request.OrgUpdateRequest;
import com.pro.request.StatusRequest;
import com.pro.service.OrganizationService;
import com.pro.sup.BackUserInfo;
import com.pro.sup.PageResult;
import com.pro.sup.Result;
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

/**
 * @Author: chengyunxiao
 * @Date: 2021/12/29/18:15
 * @Description:
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "后台权限接口")
@RequestMapping("/admin/org")
public class OrganizationController extends BackUserInfo {


    private final OrganizationService organizationService;


    @RequiresPermissions("org:view")
    @ApiOperation(value = "查询公司/机构列表", notes = "查询公司/机构列表")
    @PostMapping(value = "/orgList")
    public Result<List<Organization>> orgList() {
        List<Organization> list = organizationService.orgList();
        return Result.success(list);
    }

    @RequiresPermissions("org:view")
    @ApiOperation(value = "查询公司/机构分页列表", notes = "查询公司/机构分页列表")
    @PostMapping(value = "/orgPageList")
    public PageResult<Organization> orgPageList(@RequestBody @Valid OrgListRequest request) {
        Page<Organization> page = organizationService.orgPageList(request.getOrgName(), request.getPageNo(), request.getPageSize());
        return PageResult.success(page);
    }

    @RequiresPermissions("org:add")
    @ApiOperation(value = "添加公司/机构", notes = "添加公司/机构")
    @PostMapping(value = "/addOrg")
    public Result addOrg(@RequestBody @Valid OrgUpdateRequest request) {
        Organization organization = new Organization();
        BeanUtil.copyProperties(request, organization);
        organization.setStatus(StatusEnum.ENABLE.getIndex());
        organization.setCreateId(getLoginUserId());
        organization.insert();
        return Result.success();
    }

    @RequiresPermissions("org:edit")
    @ApiOperation(value = "编辑公司/机构", notes = "编辑公司/机构")
    @PostMapping(value = "/editOrg")
    public Result editOrg(@RequestBody @Valid OrgUpdateRequest request) {
        if (request.getId() == null) {
            throw new GlobalRuntimeException("id不能为空");
        }
        Organization organization = organizationService.getById(request.getId());
        if (organization == null) {
            throw new GlobalRuntimeException("没有找到该公司/机构");
        }
        organization.setOrgName(request.getOrgName());
        organization.setOrgCode(request.getOrgCode());
        organization.setLinkMan(request.getLinkMan());
        organization.setPhone(request.getPhone());
        organization.setAddress(request.getAddress());
        organization.setDescription(request.getDescription());
        organization.setRemark(request.getRemark());
        organization.setModifyId(getLoginUserId());
        organization.updateById();
        return Result.success();
    }

    @RequiresPermissions("org:edit")
    @ApiOperation(value = "修改公司/机构状态", notes = "修改公司/机构状态")
    @PostMapping(value = "/updateOrgStatus")
    public Result updateOrgStatus(@RequestBody @Valid StatusRequest request) {
        Organization organization = organizationService.getById(request.getId());
        if (organization == null) {
            throw new GlobalRuntimeException("没有找到该公司/机构");
        }
        organization.setStatus(StatusEnum.getType(request.getStatus()).getIndex());
        organization.setModifyId(getLoginUserId());
        organization.updateById();
        return Result.success();
    }

    @RequiresPermissions("org:delete")
    @ApiOperation(value = "删除公司/机构状态", notes = "删除公司/机构状态")
    @PostMapping(value = "/deleteOrg")
    public Result deleteOrg(@RequestBody @Valid IdRequest request) {
        Organization organization = organizationService.getById(request.getId());
        if (organization == null) {
            throw new GlobalRuntimeException("没有找到该公司/机构");
        }
        organizationService.removeById(request.getId());
        return Result.success();
    }


}
