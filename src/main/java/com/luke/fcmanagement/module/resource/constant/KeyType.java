package com.luke.fcmanagement.module.resource.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum KeyType {
    FC(1, "FC"),
    MEMBER(2, "MEMBER"),
    UNKNOWN(-1, "Không xác định");

    private final int value;
    private final String display;
}
