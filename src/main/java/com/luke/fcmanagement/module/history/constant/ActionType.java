package com.luke.fcmanagement.module.history.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActionType {
    CREATE_FC("CREATE_FC", "Thêm mới đội bóng"),
    UNKNOWN("UNKNOWN", "Không xác định");

    private final String value;
    private final String desc;
}
