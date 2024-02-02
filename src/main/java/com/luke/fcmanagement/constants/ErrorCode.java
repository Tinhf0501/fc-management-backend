package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    SUCCESS("00", "Success"),
    VALIDATE_FAIL("01", "Đầu vào không hợp lệ"),
    INTERNAL_ERROR("99","Lỗi hệ thống"),
    UNKNOWN("", "Không xác định");
    private String code;
    private String message;

    public static ErrorCode getCode(String value) {
        for (ErrorCode entity : values()) {
            if (entity.getCode().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }

}
