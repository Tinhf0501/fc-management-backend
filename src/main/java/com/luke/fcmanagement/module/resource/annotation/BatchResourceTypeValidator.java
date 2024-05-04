package com.luke.fcmanagement.module.resource.annotation;

import com.luke.fcmanagement.module.resource.constant.ResourceConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BatchResourceTypeValidator implements ConstraintValidator<BatchResourceType, List<MultipartFile>> {

    @Override
    public void initialize(BatchResourceType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext constraintValidatorContext) {
        return CollectionUtils.isEmpty(files) ||
                files.stream()
                        .map(MultipartFile::getContentType)
                        .allMatch(ResourceConstant.ALLOWED_TYPES_ALL::contains);
    }
}