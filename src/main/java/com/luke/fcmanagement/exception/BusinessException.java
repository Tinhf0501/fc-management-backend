package com.luke.fcmanagement.exception;

import com.luke.fcmanagement.constants.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode() + " - " + errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
