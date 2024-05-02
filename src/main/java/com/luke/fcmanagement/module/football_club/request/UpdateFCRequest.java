package com.luke.fcmanagement.module.football_club.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luke.fcmanagement.module.resource.annotation.BatchResourceType;
import com.luke.fcmanagement.module.resource.annotation.ResourceType;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

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
    private List<Long> pathMediaIdsDelete;
}
