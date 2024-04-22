package com.luke.fcmanagement.utils.file_utils;

import org.springframework.web.multipart.MultipartFile;

public interface FileSaver {
    String saveFile(MultipartFile file, String folderPath, String fileName);
}
