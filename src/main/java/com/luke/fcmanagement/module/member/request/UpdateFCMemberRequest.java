package com.luke.fcmanagement.module.member.request;

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
    private Long fcId;
}
