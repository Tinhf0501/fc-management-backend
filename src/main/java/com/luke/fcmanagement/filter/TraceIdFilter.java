package com.luke.fcmanagement.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.utils.Utils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class TraceIdFilter implements Filter {
    private static final String TRACE_ID_KEY = "traceId";
    private ObjectMapper mapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // * Tạo một traceId mới nếu không có traceId nào được cung cấp
            String traceId = ((HttpServletRequest) servletRequest).getHeader(TRACE_ID_KEY);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }

            // Đặt traceId vào MDC để có thể sử dụng trong log
            MDC.put(TRACE_ID_KEY, traceId);

            log.info("{} send request : {}", Utils.getIpAddress(), mapper.writeValueAsString(servletRequest.getInputStream()));

            // * Chuyển tiếp request và response cho filter tiếp theo
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            log.info("{} receive response : {}", Utils.getIpAddress(), mapper.writeValueAsString(servletResponse.getOutputStream()));
            // *  Luôn đảm bảo rằng bạn xóa traceId khỏi MDC sau khi xử lý xong
            MDC.remove(TRACE_ID_KEY);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
