package com.luke.fcmanagement.annotation;

import com.luke.fcmanagement.constants.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SaveHistory {
    ActionType value();
}
