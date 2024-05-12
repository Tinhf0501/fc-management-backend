package com.luke.fcmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse<T> {
    private List<T> items;
    private long totalItems;
    private long totalPage;
    private long page;
    private long pageSize;
}
