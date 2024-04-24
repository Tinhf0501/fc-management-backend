package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActionType {
    CREATE_FC("CREATE_FC", "Thêm mới đội bóng"),
    UNKNOWN("UNKNOWN", "Không xác định");
    private String value;
    private String desc;

    public static ActionType getType(String value) {
        for (ActionType entity : values()) {
            if (entity.getValue().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
