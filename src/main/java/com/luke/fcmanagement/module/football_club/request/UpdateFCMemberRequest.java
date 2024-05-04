package com.luke.fcmanagement.module.football_club.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFCMemberRequest extends CreateFCMemberRequest {
    @NotNull(message = "ID của thành viên không được bỏ trống")
    private Long memberId;

    @NotNull(message = "FC của thành viên không được bỏ trống")
    private Long fcId;

    private String pathAvatarDel;
}
