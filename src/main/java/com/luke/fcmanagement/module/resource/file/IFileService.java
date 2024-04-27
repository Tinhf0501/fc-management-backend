package com.luke.fcmanagement.module.resource.file;

import com.luke.fcmanagement.module.resource.constant.FCMediaType;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String saveFile(MultipartFile file, FCMediaType mediaType, String fileName);

}
