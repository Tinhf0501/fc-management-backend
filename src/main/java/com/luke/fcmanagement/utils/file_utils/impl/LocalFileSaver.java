package com.luke.fcmanagement.utils.file_utils.impl;

import com.luke.fcmanagement.utils.file_utils.FileSaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Component
/*
    @ConditionalOnProperty giúp gắn điều kiện cho @Bean dựa theo
    property trong config
     */
@ConditionalOnProperty(
        value = "file.save.type",
        havingValue = "LOCAL", // Nếu giá trị file.save.type  = LOCAL thì Bean mới được khởi tạo
        matchIfMissing = false) // matchIFMissing là giá trị mặc định nếu không tìm thấy property file.save.type
public class LocalFileSaver implements FileSaver {
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
