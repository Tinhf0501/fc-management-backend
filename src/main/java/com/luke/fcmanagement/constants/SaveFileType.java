package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SaveFileType {
    LOCAL("LOCAL", "Save file Local"),
    UNKNOWN("UNKNOWN", "Không xác định");

    private String value;
    private String display;

    public static SaveFileType getType(String value) {
        for (SaveFileType entity : values()) {
            if (entity.getValue().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
