package com.luke.fcmanagement.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAdviceAspect {

    @Before("@annotation(org.springframework.web.bind.annotation.ExceptionHandler) && args(throwable,..)")
    public void logErrorException(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }
}
