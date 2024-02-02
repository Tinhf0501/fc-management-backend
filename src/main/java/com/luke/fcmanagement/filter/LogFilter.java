package com.luke.fcmanagement.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.utils.Utils;
import com.luke.fcmanagement.wrapper.MyServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class LogFilter implements Filter {
    private static final String TRACE_ID_KEY = "traceId";
    private ObjectMapper mapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestPath = null;
        String responseString = null;
        try {
            // * Tạo một traceId mới nếu không có traceId nào được cung cấp
            HttpServletRequest request = ((HttpServletRequest) servletRequest);
            String traceId = request.getHeader(TRACE_ID_KEY);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }

            // Đặt traceId vào MDC để có thể sử dụng trong log
            MDC.put(TRACE_ID_KEY, traceId);


            requestPath = request.getRequestURI();
            // Read the body of the POST request
            StringBuilder requestBody = new StringBuilder();
            String line;
            try (BufferedReader reader = servletRequest.getReader()) {
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }

            log.info("Client IP : {} - URL : {} send request : {}", Utils.getIpAddress(), requestPath, requestBody);

            // ! Replace the original request with a wrapped request
            // ! The error "getReader() has already been called for this request" typically occurs when trying
            // !to read the request body more than once in a servlet filter or similar component.
            // ! The getReader() method can only be called once to read the request body stream.
            // ! If you need to access the request body more than once, you should read it into a local variable.
            MyServletRequestWrapper requestWrapper = new MyServletRequestWrapper(request, requestBody.toString());


            // * Chuyển tiếp request và response cho filter tiếp theo
            filterChain.doFilter(requestWrapper, servletResponse);

        } finally {

            log.info("Client IP : {} - URL : {} receive response : {}", Utils.getIpAddress(), requestPath, responseString);
            // *  Luôn đảm bảo rằng bạn xóa traceId khỏi MDC sau khi xử lý xong
            MDC.remove(TRACE_ID_KEY);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
