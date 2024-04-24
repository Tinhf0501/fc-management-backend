package com.luke.fcmanagement.module.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

public class FileUtils {
    public static boolean isMP4(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && fileName.toLowerCase().endsWith(".mp4");
    }

    public static boolean isJPG(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg"));
    }

    public static boolean isValidListFile(MultipartFile[] files) {
        return Stream.of(files)
                .anyMatch(file -> !isMP4(file) && !isJPG(file));
    }
}
