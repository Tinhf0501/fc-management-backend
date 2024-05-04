package com.luke.fcmanagement.module.history;

import com.luke.fcmanagement.constants.Status;
import com.luke.fcmanagement.module.history.annotation.CaptureHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HisLogAspect {

    private final IHisLogService historyService;

    @AfterReturning(pointcut = "@annotation(captureHistory)")
    public void saveHisSuccess(CaptureHistory captureHistory) {
        historyService.saveHisLog(Status.SUCCESS.getCode(), captureHistory.value().getValue());
    }

    @AfterThrowing(pointcut = "@annotation(captureHistory)")
    public void saveHisFail(CaptureHistory captureHistory) {
        historyService.saveHisLog(Status.FAIL.getCode(), captureHistory.value().getValue());
    }
}
