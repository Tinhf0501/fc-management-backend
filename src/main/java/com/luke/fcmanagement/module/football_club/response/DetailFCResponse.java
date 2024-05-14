package com.luke.fcmanagement.module.football_club.response;

import com.luke.fcmanagement.module.member.response.DetailMemberResponse;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetailFCResponse {
    private Long fcId;
    private String fcName;
    private String description;
    private Integer status;
    private String slug;
    private Date createdDate;
    private String logo;
    private List<String> listResource;
    private List<DetailMemberResponse> listMembers;

    public DetailFCResponse(Long fcId, String fcName, String description, Integer status, String slug, Date createdDate, String logo) {
        this.fcId = fcId;
        this.fcName = fcName;
        this.description = description;
        this.status = status;
        this.slug = slug;
        this.createdDate = createdDate;
        this.logo = logo;
    }
}
