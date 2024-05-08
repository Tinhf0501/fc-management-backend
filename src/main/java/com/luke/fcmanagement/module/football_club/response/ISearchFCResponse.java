package com.luke.fcmanagement.module.football_club.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface ISearchFCResponse {
    Long getFcId();

    String getFcName();

    String getDesc();

    Integer getTotalMembers();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getCreatedDate();

    String getCreatedBy();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date getUpdatedDate();

    String getUpdatedBy();

    Integer getStatus();

    String getSlug();
}
