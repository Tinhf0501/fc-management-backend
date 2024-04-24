package com.luke.fcmanagement.module.football_club.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CreateFCRequest {
    @NotBlank(message = "Tên FC không được bỏ trống")
    @Length(max = 255, message = "Độ dài của tên FC không được vượt quá 255 kí tự")
    private String fcName;

    @Length(max = 2000, message = "Độ dài của mô tả không được vượt quá 2000 kí tự")
    private String description;

    private Boolean isGuest;

    private List<CreateFCMemberRequest> fcMembers;

    @JsonIgnore
    private CreateFCResourceRequest fcResources;
}
