package com.luke.fcmanagement.validate.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class MultipartArraySizeValidator implements ConstraintValidator<Size, MultipartFile[]> {
    private int min;
    private int max;

    @Override
    public void initialize(Size constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile[] value, ConstraintValidatorContext constraintValidatorContext) {
        return value.length >= min && value.length <= max;
    }
}
