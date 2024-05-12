package com.luke.fcmanagement.module.football_club.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchFcRequest {
    private String fcName;
    private Integer fcStatus;
    private Date fromDate;
    private Date toDate;
}
