package com.pro.exception;

import com.pro.sup.ErrorCode;
import lombok.Data;

@Data
public class GlobalException extends Exception
{
    public ErrorCode errorCode;
    public String message;

    public GlobalException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
    public GlobalException(String message){
        this.message = message;
    }
}
