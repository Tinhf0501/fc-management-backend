package com.luke.fcmanagement.utils.file_utils;

import org.springframework.web.multipart.MultipartFile;

public class FileChecker {
    public static boolean isMP4(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && fileName.toLowerCase().endsWith(".mp4");
    }

    public static boolean isJPG(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg"));
    }

    public static boolean isValidListFile(MultipartFile[] files) {
        boolean check = true;
        for (MultipartFile file : files) {
            if (isMP4(file) || isJPG(file)) {
                check = true;
            } else {
                check = false;
                break;
            }
        }
        return check;
    }
}
