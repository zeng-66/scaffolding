package com.pro.Handler;

import com.pro.exception.GlobalException;
import com.pro.sup.ErrorCode;
import com.pro.sup.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({GlobalException.class})
    @ResponseBody
    public Result errorHandle(GlobalException e) {
        if(e.errorCode!=null){
            return Result.fail(e.errorCode);
        }else{
            return Result.fail(e.getMessage());

        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result errorHandle(MethodArgumentNotValidException e) {
        return Result.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result errorHandle(Exception e) {
        e.printStackTrace();
        log.error("系统异常：",e);
        return Result.fail(ErrorCode.SYS_ERROR);
    }
}
