package com.luke.fcmanagement.module.history.annotation;

import com.luke.fcmanagement.module.history.constant.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CaptureHistory {
    ActionType value();
}
