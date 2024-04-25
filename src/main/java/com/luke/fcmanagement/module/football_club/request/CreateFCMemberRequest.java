package com.luke.fcmanagement.module.football_club.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CreateFCMemberRequest {
    private Long userId;

    @Length(max = 255, message = "Độ dài của tên áo không được vượt quá 255 kí tự")
    private String nameShirt;

    private Integer numberShirt;

    @NotBlank(message = "Tên thành viên không được bỏ trống")
    @Length(max = 255, message = "Tên thành viên không được vượt quá 255 kí tự")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được bỏ trống")
    @Length(max = 10, message = "Số điện thoại không được vượt quá 10 kí tự")
    private String phoneNumber;

    @Length(max = 2000, message = "Địa chỉ không được vượt quá 2000 kí tự")
    private String address;

    @Length(max = 2000, message = "Mô tả không được vượt quá 2000 kí tự")
    private String description;

    @NotNull(message = "Vị trí không được bỏ trống")
    @Size(min = 1, message = "Phải có ít nhất 1 vị trí gán cho thành viên")
    private List<String> position;

    @JsonIgnore
    private MultipartFile avatar;
}
