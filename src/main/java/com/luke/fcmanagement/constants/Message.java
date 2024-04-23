package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Message {
    CREATE_FC_SUCCESS("CREATE_FC_SUCCESS", "Tạo mới đội bóng thành công!"),
    CREATE_FC_FAIL("FAIL", "Tạo mới đội bóng thất bại"),
    UNKNOWN("UNKNOWN", "Không xác định");
    private String value;
    private String desc;

    public static Message getMessage(String value) {
        for (Message entity : values()) {
            if (entity.getValue().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
