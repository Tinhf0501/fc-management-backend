package com.luke.fcmanagement.filter;

import com.luke.fcmanagement.constants.AppConstants;
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
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // * Tạo một traceId mới nếu không có traceId nào được cung cấp
            HttpServletRequest request = ((HttpServletRequest) servletRequest);
            String traceId = request.getHeader(AppConstants.TRACE_ID_KEY);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }
            // * Đặt traceId vào MDC để có thể sử dụng trong log
            MDC.put(AppConstants.TRACE_ID_KEY, traceId);
            // * Chuyển tiếp request và response cho filter tiếp theo
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // *  Luôn đảm bảo rằng bạn xóa traceId khỏi MDC sau khi xử lý xong
            MDC.remove(AppConstants.TRACE_ID_KEY);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}