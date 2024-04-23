package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusApi {
    OK("OK", "Trạng thái thành công"),
    FAIL("FAIL", "Trạng thái thất bại"),
    UNKNOWN("UNKNOWN","Không xác định");
    private String status;
    private String desc;

    public static StatusApi getStatus(String value) {
        for (StatusApi entity : values()) {
            if (entity.getStatus().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
