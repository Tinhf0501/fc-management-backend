package com.luke.fcmanagement.module.football_club.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFCRequest {
    @NotBlank(message = "Tên FC không được bỏ trống")
    @Size(max = 255, message = "Độ dài của tên FC không được vượt quá 255 kí tự")
    private String fcName;

    @Size(max = 2000, message = "Độ dài của mô tả không được vượt quá 2000 kí tự")
    private String description;

    private Boolean isGuest;

    private List<CreateFCMemberRequest> fcMembers;

    @JsonIgnore
    private CreateFCResourceRequest fcResources;
}
