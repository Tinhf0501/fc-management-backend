package com.luke.fcmanagement.exception;

import com.luke.fcmanagement.constants.ErrorCode;
import lombok.Getter;

public class RecordNotFoundException extends Exception {
    @Getter
    private ErrorCode errorCode;

    public RecordNotFoundException(String msg) {
        super(msg);
    }

    public RecordNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
