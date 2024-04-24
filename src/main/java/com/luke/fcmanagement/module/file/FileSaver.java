package com.luke.fcmanagement.module.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileSaver {
    String saveFile(MultipartFile file, String folderPath, String fileName);
}
