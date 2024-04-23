package com.luke.fcmanagement.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.model.RequestWrapper;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper mapper;

    // * Advice to log around method execution for classes annotated with @RestController
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        // * Tạo một traceId mới nếu không có traceId nào được cung cấp
        String traceId = request.getHeader(AppConstants.TRACE_ID_KEY);
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }
        // * Đặt traceId vào MDC để có thể sử dụng trong log
        MDC.put(AppConstants.TRACE_ID_KEY, traceId);
        MDC.put(AppConstants.START_TIME, String.valueOf(System.currentTimeMillis()));
        // * Log start of method execution
        log.info("Method start: " + joinPoint.getSignature().getName());
        logRequest(request);

        // * Proceed to method execution and get the result
        Object result = joinPoint.proceed();

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();
        logResponse(result, request, response);
        // * Log end of method execution
        log.info("Method end: " + joinPoint.getSignature().getName());
        // *  Luôn đảm bảo rằng bạn xóa traceId, startTime khỏi MDC sau khi xử lý xong
        MDC.clear();
        // * Return the result
        return result;
    }


    private void logRequest(HttpServletRequest request) throws IOException {
        Request requestLog = new Request(
                request.getMethod(),
                request.getRequestURI(),
                getParameters(request),
                null
        );
//        if (!Objects.equals(request.getMethod(), HttpMethod.GET.name())) {
//            requestLog.setReqBody(mapper.readValue(request.getBody(), Object.class));
//        }
        log.info("{} send request: {}", Utils.getIpAddress(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestLog));
    }

    private void logResponse(Object result, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Response responseLog = new Response(
                request.getMethod(),
                request.getRequestURI(),
                result,
                response.getStatus());

        log.info("{} : receive response: {}",
                Utils.getIpAddress(),
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseLog)
        );
    }

    private Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        return parameters;
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerMap = response.getHeaderNames();
        for (String str : headerMap) {
            headers.put(str, response.getHeader(str));
        }
        return headers;
    }

    @Data
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        private String method;
        private String path;
        private Map<String, String> params;
        private Object reqBody;
    }

    @Data
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private String method;
        private String path;
        private Object resBody;
        private int status;
    }
}
