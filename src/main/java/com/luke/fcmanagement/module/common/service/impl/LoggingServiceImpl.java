package com.luke.fcmanagement.module.common.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.module.common.service.ILoggingService;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class LoggingServiceImpl implements ILoggingService {
    private ObjectMapper mapper;

    @Override
    public void displayReq(HttpServletRequest request, Object body) {
        try {
            StringBuilder reqMessage = new StringBuilder();
            Map<String, String> parameters = getParameters(request);

            reqMessage.append("REQUEST ");
            reqMessage.append("method = [").append(request.getMethod()).append("]");
            reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");

            if (!parameters.isEmpty()) {
                reqMessage.append(" parameters = [").append(parameters).append("] ");
            }
            if (!Objects.isNull(body)) {
                String bodyString = formatJsonString(body);
                reqMessage.append(" body = [\n").append(bodyString).append("\n]");
            }

            log.info("{} send request: {}", Utils.getIpAddress(), reqMessage);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void displayResp(HttpServletRequest request, HttpServletResponse response, Object body) {
        try {
            StringBuilder respMessage = new StringBuilder();
            Map<String, String> headers = getHeaders(response);
            respMessage.append("RESPONSE ");
            respMessage.append(" method = [").append(request.getMethod()).append("]");
            if (!headers.isEmpty()) {
                respMessage.append(" ResponseHeaders = [").append(headers).append("]");
            }
            respMessage.append(" responseBody = [\n").append(formatJsonString(body)).append("\n]");

            log.info("{} : receive response: {}", Utils.getIpAddress(), respMessage);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private String formatJsonString(Object body) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(body);
        Object jsonObject = mapper.readValue(jsonString, Object.class);
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        return prettyJson;
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerMap = response.getHeaderNames();
        for (String str : headerMap) {
            headers.put(str, response.getHeader(str));
        }
        return headers;
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
}
