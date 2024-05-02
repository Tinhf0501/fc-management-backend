package com.luke.fcmanagement.module.resource.annotation;

import com.luke.fcmanagement.module.resource.constant.MediaType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResourceTypeValidator.class)
@Documented
public @interface ResourceType {
    MediaType value() default MediaType.UNKNOWN;

    String message() default "Invalid file type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
