package com.luke.fcmanagement.module.resource.annotation;

import com.luke.fcmanagement.module.resource.constant.ResourceConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class BatchResourceTypeValidator implements ConstraintValidator<BatchResourceType, List<MultipartFile>> {

    @Override
    public void initialize(BatchResourceType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext constraintValidatorContext) {
        if (files == null || files.isEmpty()) {
            return true; // Allow empty list
        }

        for (MultipartFile file : files) {
            if (file == null || !isValidFileType(file)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFileType(MultipartFile file) {
        return file != null && ResourceConstant.ALLOWED_TYPES_ALL.contains(file.getContentType());
    }
}
