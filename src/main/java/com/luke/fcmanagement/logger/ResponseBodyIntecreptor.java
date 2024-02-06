package com.luke.fcmanagement.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ResponseBodyIntecreptor implements ResponseBodyAdvice<Object> {
    private final ObjectMapper mapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        displayResp(((ServletServerHttpRequest) request).getServletRequest(), ((ServletServerHttpResponse) response).getServletResponse(), body);
        return body;
    }

    public void displayResp(HttpServletRequest request, HttpServletResponse response, Object body) {
        try {
            Response responseLog = Response.builder().
                    method(request.getMethod()).
                    path(request.getRequestURI()).
                    status(response.getStatus()).
                    resBody(body).build();
            log.info("{} : receive response: {}", Utils.getIpAddress(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseLog));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
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
    public static class Response {
        private String method;
        private String path;
        private Object resBody;
        private int status;
    }
}
