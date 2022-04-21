package com.pro.controller;

import com.pro.enums.FileType;
import com.pro.exception.GlobalException;
import com.pro.helper.OssHelper;
import com.pro.sup.ErrorCode;
import com.pro.sup.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("/api/file")
@Api(tags="APP文件上传")
public class FileController {
    private final OssHelper ossHelper;

    @PostMapping(value = "/updateLoadImage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "上传图片", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result  updateLoadImage(MultipartFile uploadFile) throws GlobalException {
        if (uploadFile == null) {
            return Result.fail(ErrorCode.PARAM_ERROR);
        }
        return Result.success( ossHelper.uploadFile(uploadFile,FileType.IMAGE));
    }

    @PostMapping(value = "/updateLoadVideo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "上传视频", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result  updateLoadVideo(MultipartFile uploadFile) throws GlobalException {
        if (uploadFile == null) {
            return Result.fail(ErrorCode.PARAM_ERROR);
        }
        return Result.success( ossHelper.uploadFile(uploadFile, FileType.VIDEO));
    }



}