package com.luke.fcmanagement.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class LogFilter extends OncePerRequestFilter {
    private ObjectMapper mapper;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            // * Tạo một traceId mới nếu không có traceId nào được cung cấp
            final ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(httpServletResponse);

            String traceId = httpServletRequest.getHeader(AppConstants.TRACE_ID_KEY);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }
            // * Đặt traceId vào MDC để có thể sử dụng trong log
            MDC.put(AppConstants.TRACE_ID_KEY, traceId);

            Request requestLog = new Request(
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    getParameters(httpServletRequest),
                    null
            );
            if (!Objects.equals(httpServletRequest.getMethod(), HttpMethod.GET.name())) {
                requestLog.setReqBody(mapper.readValue(httpServletRequest.getInputStream(), Object.class));
            }
            log.info("{} send request: {}", Utils.getIpAddress(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestLog));
            // * Chuyển tiếp request và response cho filter tiếp theo
            filterChain.doFilter(httpServletRequest, httpServletResponse);

            Object object = getResponseBody(resp);
            Response responseLog = new Response(
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    object,
                    httpServletResponse.getStatus());
            log.info("{} : receive response: {}", Utils.getIpAddress(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseLog));


        } finally {
            // *  Luôn đảm bảo rằng bạn xóa traceId khỏi MDC sau khi xử lý xong
            MDC.remove(AppConstants.TRACE_ID_KEY);
        }
    }

    private Object getResponseBody(ContentCachingResponseWrapper response) throws IOException {
        final byte[] responseByte = response.getContentAsByteArray();
        if (responseByte.length > 0) {
            // If there is a response body, parse it into the desired object
            String responseBodyString = new String(responseByte, StandardCharsets.UTF_8);

            // Use Jackson to deserialize the response content into the specified object type
            return mapper.readValue(responseBodyString, Object.class);
        } else {
            // Handle the case where the response body is empty
            return null;
        }
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
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        private String method;
        private String path;
        private Map<String, String> params;
        private Object reqBody;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private String method;
        private String path;
        private Object resBody;
        private int status;
    }

}