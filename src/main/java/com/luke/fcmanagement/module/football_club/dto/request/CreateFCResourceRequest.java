package com.luke.fcmanagement.module.football_club.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.luke.fcmanagement.validate.validator.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFCResourceRequest {
    @JsonIgnore
    private MultipartFile logo;

    @JsonIgnore
    @Size(max = 10, message = "Số lượng file tối đa được tải lên là 10.")
    private MultipartFile[] media;
}
