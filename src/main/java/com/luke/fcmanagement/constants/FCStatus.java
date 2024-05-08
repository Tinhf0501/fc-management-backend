package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FCStatus {
    ACTIVE(1, "Hoạt động"),
    CLOSE(2, "Đóng"),
    INACTIVE(3, "Chưa hoạt động"),
    UNKNOWN(-1, "Không xác định");

    private final int value;
    private final String desc;

    public static FCStatus getStatus(Integer value) {
        if (Objects.isNull(value))
            return UNKNOWN;
        for (FCStatus status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
