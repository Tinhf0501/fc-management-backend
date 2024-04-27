package com.luke.fcmanagement.module.resource.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FCMediaType {
    IMAGE("IMAGE", "Image", "media.img-path"),
    VIDEO("VIDEO", "Video", "media.video-path"),
    UNKNOWN("UNKNOWN", "Không xác định", "");

    private final String value;
    private final String display;
    private final String folderEnvKey;
}
