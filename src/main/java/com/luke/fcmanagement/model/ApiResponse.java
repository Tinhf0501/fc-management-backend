package com.luke.fcmanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.constants.StatusApi;
import lombok.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private ApiBody apiBody;
    private ApiError apiError;
    private ErrorCode code;
    private StatusApi status;

    @Setter(AccessLevel.PRIVATE)
    private String traceId;

    @Setter(AccessLevel.PRIVATE)
    private long duration;

    public ApiResponse(Object o, ApiError apiError, String code, String status) {
    }

    public static ApiResponse ok(ApiBody apiBody) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(StatusApi.OK);
        apiResponse.setCode(ErrorCode.SUCCESS);
        apiResponse.setApiBody(apiBody);
        return apiResponse;
    }

    public static ApiResponse fail(ErrorCode code, ApiError apiError) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(StatusApi.FAIL);
        apiResponse.setCode(code);
        apiResponse.setApiError(apiError);
        return apiResponse;
    }

    public String getTraceId() {
        return MDC.get(AppConstants.TRACE_ID_KEY);
    }

    public long getDuration() {
        final long endTime = System.currentTimeMillis();
        final String startTime = MDC.get(AppConstants.START_TIME);
        final long startTimeNum = NumberUtils.toLong(startTime, endTime);
        return endTime - startTimeNum;
    }
}

