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
import org.springframework.web.multipart.MultipartFile;

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

    private List<CreateFCMemberRequest> fcMembers;

    @JsonIgnore
    @NotNull(message = "Logo FC không được bỏ trống")
    @ResourceType(value = MediaType.IMAGE, message = "File logo phải là file .png hoặc .jpg(.jpeg)")
    private MultipartFile logo;

    @JsonIgnore
    @Size(max = 10, message = "Số lượng file tối đa được tải lên là 10.")
    @BatchResourceType(message = "File media phải là file .png, .jpg(.jpeg), .mp4")
    private List<MultipartFile> media;
}
