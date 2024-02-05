package com.luke.fcmanagement.logger;

import com.luke.fcmanagement.module.common.service.ILoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class InterceptLog implements HandlerInterceptor {
    ILoggingService loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET.name())
                || request.getMethod().equals(HttpMethod.DELETE.name())
                || request.getMethod().equals(HttpMethod.PUT.name())
                || request.getMethod().equals(HttpMethod.POST.name())
        ) {
            loggingService.displayReq(request, null);
        }
        return true;
    }
}
