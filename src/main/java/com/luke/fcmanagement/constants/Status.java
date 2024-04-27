package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
    SUCCESS("SUCCESS", "Thao tác thành công"),
    FAIL("FAIL", "Tháo tác thất bại"),
    UNKNOWN("UNKNOWN", "Không xác định");

    private final String code;
    private final String value;
}
