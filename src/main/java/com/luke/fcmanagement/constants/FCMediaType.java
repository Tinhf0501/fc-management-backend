package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FCMediaType {
    LOGO("LOGO", "Logo FC"),
    IMAGE("IMAGE", "Image FC"),
    VIDEO("VIDEO", "Video FC"),
    UNKNOWN("UNKNOWN", "Không xác định");

    private String value;
    private String display;

    public static FCMediaType getType(String value) {
        for (FCMediaType entity : values()) {
            if (entity.getValue().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
