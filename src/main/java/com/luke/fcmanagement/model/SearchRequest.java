package com.luke.fcmanagement.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchRequest<T> {
    @NotNull
    private T data;

    @NotNull
    @Min(value = 1, message = "pageNo >= 1")
    private Integer pageNo;

    @NotNull
    @Max(value = 500, message = "pageSize <= 500")
    private Integer pageSize;
}
