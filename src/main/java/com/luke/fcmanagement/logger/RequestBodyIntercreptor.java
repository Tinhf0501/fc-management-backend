package com.luke.fcmanagement.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.*;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RequestBodyIntercreptor extends RequestBodyAdviceAdapter {
    private final HttpServletRequest request;
    private final ObjectMapper mapper;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        displayReq(request, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    public void displayReq(HttpServletRequest request, Object body) {
        try {
            Request requestLog = Request.builder().
                    params(getParameters(request)).
                    method(request.getMethod()).
                    path(request.getRequestURI()).
                    reqBody(body).build();
            log.info("{} send request: {}", Utils.getIpAddress(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestLog));
        } catch (Exception ex) {
            log.error(ex.getMessage());
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

    @Data
    @Builder
    public static class Request {
        private String method;
        private String path;
        private Map<String, String> params;
        private Object reqBody;
    }
}
