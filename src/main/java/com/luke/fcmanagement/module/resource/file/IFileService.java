package com.luke.fcmanagement.module.resource.file;

import com.luke.fcmanagement.module.resource.constant.MediaType;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String saveFile(MultipartFile file, MediaType mediaType, String fileName);

    void deleteFile(String path);
}
