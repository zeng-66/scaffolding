package com.pro.Handler;

import com.pro.exception.GlobalRuntimeException;
import com.pro.sup.ErrorCode;
import com.pro.sup.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
@Order(0)
public class GlobalRuntimeExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GlobalRuntimeException.class)
    @ResponseBody
    public Result handleRRException(GlobalRuntimeException e){
        String message = e.getMessage();
        if (e.getErrorCode() != null){
            return Result.fail(e.getErrorCode());
        }
        log.error(message, e);
        return Result.fail(message);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result errorHandle(MethodArgumentNotValidException e) {
        return Result.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(ErrorCode.PATH_IS_NOT_EXIT);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException e){
        log.error(e.getMessage(), e);
        return Result.fail("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e){
        log.error(e.getMessage(), e);
        return Result.fail("操作失败，"+e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> IllegalArgumentException(Exception e){
        log.error(e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

}
