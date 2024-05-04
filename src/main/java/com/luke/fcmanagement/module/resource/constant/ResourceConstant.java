package com.luke.fcmanagement.module.resource.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceConstant {
    public static final Set<String> EXTENSION_IMAGE = Set.of(".png", ".jpg", ".jpeg");
    public static final Set<String> ALLOWED_TYPES_IMAGE = Set.of("image/jpeg", "image/png");
    public static final Set<String> ALLOWED_TYPES_VIDEO = Set.of("video/mp4");
    public static final Set<String> ALLOWED_TYPES_ALL = Set.of("image/jpeg", "image/png", "video/mp4");
}
