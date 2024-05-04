package com.luke.fcmanagement.module.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.module.logging.model.RequestWrapper;
import com.luke.fcmanagement.utils.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final LoggingProperties loggingProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        return this.loggingProperties.getIgnoresPath().stream().anyMatch(path -> new AntPathMatcher().match(path, requestURI));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String contentType = request.getContentType();
        final ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);
        String requestUri = null;
        try {
            final String traceId = this.generateTraceId(request);
            MDC.put(AppConstants.TRACE_ID_KEY, traceId);
            MDC.put(AppConstants.START_TIME, String.valueOf(System.currentTimeMillis()));
            if (Objects.isNull(contentType) || !contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                final RequestWrapper requestWrapper = new RequestWrapper(request);
                final Object requestBody = this.getRequestBody(requestWrapper);
                requestUri = requestWrapper.getRequestURI();
                this.preHandle(requestBody, requestUri);
                filterChain.doFilter(requestWrapper, resp);
            } else {
                filterChain.doFilter(request, resp);
            }
        } finally {
            final Object responseBody = this.getResponseBody(resp);
            this.postHandle(responseBody, requestUri);
            resp.copyBodyToResponse();
            MDC.clear();
        }
    }

    private String generateTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(AppConstants.TRACE_ID_KEY);
        if (StringUtils.isNoneBlank(traceId)) {
            return traceId;
        }
        return UUID.randomUUID().toString();
    }

    protected void preHandle(Object requestBody, String requestUri) {
        log.info("API : {} send REQUEST : body-{}", requestUri, JSON.stringify(requestBody));
    }

    protected void postHandle(Object responseBody, String requestUri) {
        log.info("API : {} receive RESPONSE : body-{}", requestUri, JSON.stringify(responseBody));
    }

    private Object getRequestBody(RequestWrapper request) {
        final String json = request.getBody();
        try {
            return this.objectMapper.readValue(json, Object.class);
        } catch (Exception ex) {
            return json;
        }
    }

    private Object getResponseBody(ContentCachingResponseWrapper response) {
        try {
            final byte[] responseByte = response.getContentAsByteArray();
            return this.objectMapper.readValue(responseByte, Object.class);
        } catch (Exception ex) {
            return new HashMap<>();
        }
    }
}