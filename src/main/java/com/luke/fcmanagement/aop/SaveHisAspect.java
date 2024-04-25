package com.luke.fcmanagement.aop;

import com.luke.fcmanagement.annotation.SaveHistory;
import com.luke.fcmanagement.constants.Status;
import com.luke.fcmanagement.module.history.HistoryService;
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
public class SaveHisAspect {

    private final HistoryService historyService;

    @AfterReturning(pointcut = "@annotation(saveHistory)")
    public void saveHisSuccess(SaveHistory saveHistory) {
        historyService.saveHisLog(Status.SUCCESS.getStatus(), saveHistory.value().getValue());
    }

    @AfterThrowing(pointcut = "@annotation(saveHistory)")
    public void saveHisFail(SaveHistory saveHistory) {
        historyService.saveHisLog(Status.FAIL.getStatus(), saveHistory.value().getValue());
    }
}
