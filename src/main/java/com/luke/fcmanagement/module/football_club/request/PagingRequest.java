package com.luke.fcmanagement.module.football_club.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingRequest {
    @Min(value = 1, message = "pageNo nhỏ nhất phải là 1")
    private Integer pageNo;

    @Max(value = 500, message = "số lượng bản ghi của 1 trang lớn nhất là 500")
    private Integer pageSize;
}
