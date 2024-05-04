package com.luke.fcmanagement.module.resource.annotation;

import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.constant.ResourceConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ResourceTypeValidator implements ConstraintValidator<ResourceType, MultipartFile> {
    private MediaType mediaType;

    @Override
    public void initialize(ResourceType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.mediaType = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(mediaType) || Objects.isNull(file))
            return true;
        else if (Objects.equals(MediaType.IMAGE, mediaType))
            return ResourceConstant.ALLOWED_TYPES_IMAGE.contains(file.getContentType());
        else if (Objects.equals(MediaType.VIDEO, mediaType))
            return ResourceConstant.ALLOWED_TYPES_VIDEO.contains(file.getContentType());
        else
            return false;
    }
}
