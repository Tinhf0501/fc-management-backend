package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
    SUCCESS("SUCCESS", "Thao tác thành công"),
    FAIL("FAIL", "Tháo tác thất bại"),
    UNKNOWN("UNKNOWN", "Không xác định");
    private String status;
    private String desc;

    public static Status getStatus(String value) {
        for (Status entity : values()) {
            if (entity.getStatus().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
