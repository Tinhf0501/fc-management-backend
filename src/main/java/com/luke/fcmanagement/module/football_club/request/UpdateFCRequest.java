package com.luke.fcmanagement.module.football_club.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateFCRequest {
    @NotNull(message = "ID của FC không được bỏ trống")
    private Long fcId;

    @NotBlank(message = "Tên FC không được bỏ trống")
    @Length(max = 255, message = "Độ dài của tên FC không được vượt quá 255 kí tự")
    private String fcName;

    @Length(max = 2000, message = "Độ dài của mô tả không được vượt quá 2000 kí tự")
    private String description;

    private List<CreateFCMemberRequest> fcMembersAdd;
    private List<UpdateFCMemberRequest> fcMemberUpdate;

    @JsonIgnore
    private MultipartFile logoNew;

    @JsonIgnore
    @Size(max = 10, message = "Số lượng file tối đa được tải lên là 10.")
    private List<MultipartFile> mediaNew;

    private List<String> listPathMediaDelete;
}
