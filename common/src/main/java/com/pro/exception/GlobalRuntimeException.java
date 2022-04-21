package com.pro.exception;

import com.pro.sup.ErrorCode;
import lombok.Data;

@Data
public class GlobalRuntimeException extends RuntimeException
{
    public ErrorCode errorCode;
    public String message;

    public GlobalRuntimeException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
    public GlobalRuntimeException(String message){
        this.message = message;
    }
}
