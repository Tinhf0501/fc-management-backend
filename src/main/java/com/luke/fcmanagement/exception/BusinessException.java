package com.luke.fcmanagement.exception;

import com.luke.fcmanagement.constants.ErrorCode;
import lombok.Getter;

public class BusinessException extends Exception {
    @Getter
    private ErrorCode errorCode;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
