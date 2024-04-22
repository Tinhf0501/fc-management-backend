package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FCStatus {
    ACTIVE("1", "Hoạt động", "label label-lg font-weight-bold label-light-success label-inline"),
    CLOSE("2", "Đóng", "label label-lg font-weight-bold label-light-gray label-inline"),
    INACTIVE("3", "Chưa hoạt động", "label label-lg font-weight-bold label-light-info label-inline"),
    UNKNOWN("", "Không xác định", "");

    private final String value;
    private final String display;
    private final String cssClass;

    public static FCStatus getStatus(String value) {
        for (FCStatus entity : values()) {
            if (entity.getValue().equals(value)) {
                return entity;
            }
        }
        return UNKNOWN;
    }
}
