package com.luke.fcmanagement.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Utils {
    public static String getIpAddress() {
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
        return "Client IP Address: " + ipAddress;
    }
}
