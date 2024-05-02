package com.luke.fcmanagement.module.resource.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceConstant {
    public static final Set<String> EXTENSION_IMAGE = Set.of(".png", ".jpg", ".jpeg");
    public static final List<String> ALLOWED_TYPES_IMAGE = Arrays.asList("image/jpeg", "image/png");
    public static final List<String> ALLOWED_TYPES_VIDEO = Arrays.asList("video/mp4");
    public static final List<String> ALLOWED_TYPES_ALL = Arrays.asList("image/jpeg", "image/png", "video/mp4");
}
