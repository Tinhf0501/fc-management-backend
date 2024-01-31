package com.luke.fcmanagement.utils;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.constants.StatusApi;
import com.luke.fcmanagement.model.ApiError;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.model.ErrorMsg;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseUtils {
    private static SpringValidatorAdapter springValidatorAdapter;

    public ResponseUtils(SpringValidatorAdapter springValidatorAdapter) {
        ResponseUtils.springValidatorAdapter = springValidatorAdapter;
    }

    public static ApiResponse setResponseValidate(BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.VALIDATE_FAIL.getCode());
        apiResponse.setStatus(StatusApi.FAIL.getStatus());

//        String mgs = String.join(", ",
//                bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList());
        Map<String, Object> err = new HashMap<>();
        bindingResult.getFieldErrors().stream().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String messageErr = fieldError.getDefaultMessage();
            err.put(fieldName, messageErr);
        });
        ApiError apiError = new ApiError();
        apiError.setErrorMsg(new ErrorMsg(ErrorCode.VALIDATE_FAIL.getMessage(), err));
        apiResponse.setApiError(apiError);
        return apiResponse;
    }

    public static ApiResponse setOKResponse(ApiResponse apiResponse) {
        apiResponse.setCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setStatus(StatusApi.OK.getStatus());
        return apiResponse;
    }

    public static ApiResponse validateObject(Object object) {
        DirectFieldBindingResult directFieldBindingResult = new DirectFieldBindingResult(object, "USER");
        springValidatorAdapter.validate(object, directFieldBindingResult);
        if (directFieldBindingResult.hasErrors()) {
            return setResponseValidate(directFieldBindingResult);
        }
        return null;
    }

}
