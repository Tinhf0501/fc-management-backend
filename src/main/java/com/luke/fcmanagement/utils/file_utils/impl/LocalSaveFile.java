package com.luke.fcmanagement.utils.file_utils.impl;

import com.luke.fcmanagement.utils.file_utils.FileSaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class LocalSaveFile implements FileSaver {
    @Value("${media.absolute-path}")
    private String pathAbsolute;

    @Override
    public String saveFile(MultipartFile file, String folderPath, String fileName) {
        try {
            String pathSave = folderPath + fileName;
            file.transferTo(new File(pathAbsolute + pathSave));
            return pathSave;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
