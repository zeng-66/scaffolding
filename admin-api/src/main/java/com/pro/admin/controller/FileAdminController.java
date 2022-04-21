package com.pro.admin.controller;

import com.pro.exception.GlobalException;
import com.pro.helper.OssHelper;
import com.pro.sup.ErrorCode;
import com.pro.sup.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 菜单表服务控制器
 *
 * @author zhangxiangyu
 * @since 2021-04-03 15:15:03
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/file")
@Api(tags="后台文件上传模块")
public class FileAdminController {
    private final OssHelper ossHelper;

    @PostMapping(value = "/updateLoad", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "后台上传文件", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "uploadFile", value = "文件流对象", required = true, dataType = "__File")})
    public Result  updateLoad(MultipartFile uploadFile) throws GlobalException {
        if (uploadFile == null) {
            return Result.fail(ErrorCode.PARAM_ERROR);
        }
        return Result.success( ossHelper.uploadFile(uploadFile,null));
    }



}