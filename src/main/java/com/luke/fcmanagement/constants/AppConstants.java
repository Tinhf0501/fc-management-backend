package com.luke.fcmanagement.constants;

public class AppConstants {
    public static final String TRACE_ID_KEY = "traceId";
    public static final String START_TIME = "startTime";

    public static final String CONTENT_TYPE_MULTI_PART = "multipart/form-data";
    public static final long PASSWORD_EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L; // 30 days
    public static final int MAX_FAILED_ATTEMPTS = 3;
    public static final long LOCK_TIME_DURATION = 2 * 60 * 1000; // 2 minutes
}
