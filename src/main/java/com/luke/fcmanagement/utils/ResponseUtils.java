package com.luke.fcmanagement.utils;

import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.model.ApiResponse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;

@UtilityClass
public class ResponseUtils {

    public void setTraceIdAndDuration(ApiResponse response) {
        final long endTime = System.currentTimeMillis();
        final String startTime = MDC.get(AppConstants.START_TIME);
        final long startTimeNum = NumberUtils.toLong(startTime, endTime);
        response.setTraceId(MDC.get(AppConstants.TRACE_ID_KEY));
        response.setDuration(endTime - startTimeNum);
    }

}
