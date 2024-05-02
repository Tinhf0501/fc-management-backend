package com.luke.fcmanagement.module.football_club.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFCMemberRequest extends CreateFCMemberRequest {
    private Long memberId;
}
