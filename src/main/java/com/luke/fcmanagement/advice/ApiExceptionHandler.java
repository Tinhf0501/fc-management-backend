package com.luke.fcmanagement.advice;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.constants.StatusApi;
import com.luke.fcmanagement.model.ApiError;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.model.ErrorMsg;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleAllException(Exception ex, WebRequest request) {
        ErrorMsg errorMessage = new ErrorMsg(ex.getLocalizedMessage(), null);
        ApiError apiError = new ApiError(errorMessage);
        return new ApiResponse(null, apiError, ErrorCode.INTERNAL_ERROR.getCode(), StatusApi.FAIL.getStatus(),0L);
    }
}
