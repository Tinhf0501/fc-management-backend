package com.luke.fcmanagement.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum JobType {
    DELETE_RESOURCE("DELETE_RESOURCE", "Job xóa file"),
    UNKNOWN("", "Không xác định");

    private final String value;
    private final String desc;
}
