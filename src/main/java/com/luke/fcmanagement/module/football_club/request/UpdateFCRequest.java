package com.luke.fcmanagement.module.football_club.request;

import com.luke.fcmanagement.module.member.request.UpdateFCMemberRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFCRequest extends CreateFCRequest {
    @NotNull(message = "ID của FC không được bỏ trống")
    private Long fcId;
    private List<UpdateFCMemberRequest> fcMemberUpdate;
    private List<Long> fcMemberIdsDelete;
    private List<String> pathMediaDelete;
}
