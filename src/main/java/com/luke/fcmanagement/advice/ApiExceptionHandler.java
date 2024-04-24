package com.luke.fcmanagement.advice;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiError;
import com.luke.fcmanagement.model.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleAllException(Exception ex) {
        String msg = StringUtils.isBlank(ex.getLocalizedMessage()) ? ex.toString() : ex.getLocalizedMessage();
        ApiError apiError = new ApiError(msg, null);
        return ApiResponse.fail(ErrorCode.INTERNAL_ERROR, apiError);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse handleBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, Object> err = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        ApiError apiError = new ApiError(ErrorCode.VALIDATE_FAIL.getMessage(), err);
        return ApiResponse.fail(ErrorCode.VALIDATE_FAIL, apiError);
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleBusinessException(BusinessException ex) {
        return ApiResponse.fail(ex.getErrorCode());
    }

}
