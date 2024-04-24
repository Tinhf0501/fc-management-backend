package com.luke.fcmanagement.utils;

import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.entity.HisLogEntity;
import com.luke.fcmanagement.repository.HisLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Component
public class Utils {
    private final HisLogRepository hisLogRepository;

    public static String getIpAddress() {
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
        return ipAddress;
    }

    public void saveHisLog(String status, String actionType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        HisLogEntity hisLog = HisLogEntity
                .builder()
                .traceID(MDC.get(AppConstants.TRACE_ID_KEY))
                .status(status)
                .action(actionType)
                .apiPath(request.getRequestURI())
                .build();
        hisLogRepository.save(hisLog);
    }
}
