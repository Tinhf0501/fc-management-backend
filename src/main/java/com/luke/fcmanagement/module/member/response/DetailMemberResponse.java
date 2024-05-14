package com.luke.fcmanagement.module.member.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DetailMemberResponse {
    private Long memberId;
    private String avatar;
    private String nameShirt;
    private Integer numberShirt;
    private String position;
    private String fullName;
    private String phoneNumber;
    private String address;
}
