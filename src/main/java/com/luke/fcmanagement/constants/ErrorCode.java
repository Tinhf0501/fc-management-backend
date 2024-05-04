package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    SUCCESS("00", "Success"),
    VALIDATE_FAIL("01", "Đầu vào không hợp lệ"),
    NOT_FOUND_RECORD("02", "Không tìm thấy bản ghi tương ứng"),
    INTERNAL_ERROR("99","Lỗi hệ thống"),
    UNKNOWN("", "Không xác định");

    private final String code;
    private final String message;
}
