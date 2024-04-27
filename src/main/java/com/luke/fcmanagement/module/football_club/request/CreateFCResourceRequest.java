package com.luke.fcmanagement.module.football_club.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CreateFCResourceRequest {
    @JsonIgnore
    private MultipartFile logo;

    @JsonIgnore
    @Size(max = 10, message = "Số lượng file tối đa được tải lên là 10.")
    private List<MultipartFile> media;
}
