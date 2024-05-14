package com.luke.fcmanagement.module.football_club.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luke.fcmanagement.module.football_club.FCStatus;
import com.luke.fcmanagement.module.member.MemberEntity;
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

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFCMemberRequest {
    private Long userId;

    @Size(max = 255, message = "Độ dài của tên áo không được vượt quá 255 kí tự")
    private String nameShirt;

    private Integer numberShirt;

    @NotBlank(message = "Tên thành viên không được bỏ trống")
    @Size(max = 255, message = "Tên thành viên không được vượt quá 255 kí tự")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 kí tự")
    private String phoneNumber;

    @Size(max = 2000, message = "Địa chỉ không được vượt quá 2000 kí tự")
    private String address;

    private LocalDate dateOfBirthday;

    @Size(max = 2000, message = "Mô tả không được vượt quá 2000 kí tự")
    private String description;

    @NotNull(message = "Vị trí không được bỏ trống")
    @Size(min = 1, message = "Phải có ít nhất 1 vị trí gán cho thành viên")
    private List<String> position;

    @JsonIgnore
    @ResourceType(MediaType.IMAGE)
    private MultipartFile avatar;

    public MemberEntity toEntity(Long fcId, FCStatus fcStatus) {
        return MemberEntity
                .builder()
                .userId(this.getUserId())
                .fcId(fcId)
                .nameShirt(this.getNameShirt())
                .numberShirt(this.getNumberShirt())
                .fullName(this.getFullName())
                .phoneNumber(this.getPhoneNumber())
                .address(this.getAddress())
                .description(this.getDescription())
                .position(String.join(",", this.getPosition()))
                .status(fcStatus.getValue())
                .dateOfBirthday(this.getDateOfBirthday())
                .build();
    }
}
