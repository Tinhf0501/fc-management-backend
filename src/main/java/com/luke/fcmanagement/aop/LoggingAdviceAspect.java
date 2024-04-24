package com.luke.fcmanagement.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.model.log.Response;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAdviceAspect {
    private final ObjectMapper mapper;

    // * Advice to log around method execution for classes annotated with @RestController
    @Around("@within(org.springframework.web.bind.annotation.RestControllerAdvice)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // * Log start of method execution
        log.info("Method start: " + joinPoint.getSignature().getName());
        // * Proceed to method execution and get the result
        Object result = joinPoint.proceed();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        logResponse(result, request);
        // * Log end of method execution
        log.info("Method end: " + joinPoint.getSignature().getName());
        // *  Luôn đảm bảo rằng bạn xóa traceId, startTime khỏi MDC sau khi xử lý xong
        MDC.clear();
        // * Return the result
        return result;
    }

    private void logResponse(Object result, HttpServletRequest request) throws IOException {
        Response responseLog = new Response(
                request.getMethod(),
                request.getRequestURI(),
                result,
                null);

        log.info("{} : receive response: {}",
                Utils.getIpAddress(),
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseLog)
        );
    }
}
