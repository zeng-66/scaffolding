package com.pro.admin.controller;

import com.pro.entity.BackAuthorities;
import com.pro.entity.BackUser;
import com.pro.request.LoginRequest;
import com.pro.request.UpdatePswRequest;
import com.pro.service.BackAuthoritiesService;
import com.pro.service.BackUserService;
import com.pro.shiro.EndecryptUtil;
import com.pro.sup.BackUserInfo;
import com.pro.sup.Result;
import com.pro.vo.IndexResponse;
import com.pro.vo.MenuTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "后台系统接口")
@RequestMapping("/admin/system")
@RequiredArgsConstructor
public class SystemController extends BackUserInfo {

    private final BackUserService backUserService;
    private final BackAuthoritiesService backAuthoritiesService;

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "/login")
    public Result login(@Valid @RequestBody LoginRequest request){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(request.getUserName(), request.getPassword());
            SecurityUtils.getSubject().login(token);
            //更新最后登录时间
            backUserService.updateLastLoginTime(request.getUserName());
            return Result.success();
        } catch (IncorrectCredentialsException ice) {
            return Result.fail("密码错误");
        } catch (UnknownAccountException uae) {
            return Result.fail("账号不存在");
        } catch (LockedAccountException e) {
            return Result.fail("账号被锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.fail("操作频繁，请稍后再试");
        }
    }

    @ApiOperation(value = "退出", notes = "退出")
    @PostMapping(value = "/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    @ApiOperation(value = "首页", notes = "首页")
    @PostMapping(value = "/index")
    public Result<IndexResponse> index(){
        List<BackAuthorities> authorities = backAuthoritiesService.listByUserId(getLoginUserId());
        List<MenuTreeVo> menuTree = backAuthoritiesService.getMenuTree(authorities, -1L);
        IndexResponse response = new IndexResponse();
        BackUser backUser = getLoginUser();
        response.setMenuTree(menuTree);
        response.setBackUser(backUser);
        return Result.success(response);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping(value = "/updatePsw")
    public Result updatePsw(@Valid @RequestBody UpdatePswRequest request){
        if ("admin".equals(getLoginUser().getUserName())) {
            return Result.fail("admin账号关闭该功能");
        }
        String finalSecret = EndecryptUtil.encrytMd5(request.getOldPsw(), getLoginUserName(), 3);
        if (!finalSecret.equals(getLoginUser().getPassword())) {
            return Result.fail("原密码输入不正确");
        }
        backUserService.updatePsw(getLoginUserId(), getLoginUserName(), EndecryptUtil.encrytMd5(request.getNewPsw(), getLoginUserName(), 3));
        return Result.success();
    }


}
