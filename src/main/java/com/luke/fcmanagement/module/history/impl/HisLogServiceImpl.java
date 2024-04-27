package com.luke.fcmanagement.module.history.impl;

import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.module.history.HisLogEntity;
import com.luke.fcmanagement.module.history.IHisLogRepository;
import com.luke.fcmanagement.module.history.IHisLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class HisLogServiceImpl implements IHisLogService {
    private final IHisLogRepository hisLogRepository;

    @Override
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
        this.hisLogRepository.save(hisLog);
    }
}
