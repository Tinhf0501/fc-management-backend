package com.luke.fcmanagement.module.file;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@UtilityClass
public class FileUtils {
    public boolean isMP4(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && fileName.toLowerCase().endsWith(".mp4");
    }

    public boolean isJPG(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg"));
    }

    public boolean isValidListFile(List<MultipartFile> files) {
        if (Objects.isNull(files) || files.isEmpty())
            return false;
        return files.stream()
                .anyMatch(file -> !isMP4(file) && !isJPG(file));
    }
}
