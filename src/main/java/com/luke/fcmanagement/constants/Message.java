package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Message {
    CREATE_FC_SUCCESS("CREATE_FC_SUCCESS", "Tạo mới đội bóng thành công!"),
    CREATE_FC_FAIL("FAIL", "Tạo mới đội bóng thất bại"),
    UNKNOWN("UNKNOWN", "Không xác định");

    private final String code;
    private final String value;
}
