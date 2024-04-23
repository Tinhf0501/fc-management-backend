package com.luke.fcmanagement.utils.file_utils.impl;

import com.luke.fcmanagement.utils.file_utils.FileSaver;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
        havingValue = "LOCAL", // Nếu giá trị loda.bean2.enabled  = true thì Bean mới được khởi tạo
        matchIfMissing = false) // matchIFMissing là giá trị mặc định nếu không tìm thấy property loda.bean2.enabled
public class LocalFileSaver implements FileSaver {
    @Value("${media.absolute-path}")
    private String pathAbsolute;

    @Override
    public String saveFile(MultipartFile file, String folderPath, String fileName) {
        try {
            System.out.println(pathAbsolute);
            String pathSave = folderPath + fileName;
            file.transferTo(new File(pathAbsolute + pathSave));
            return pathSave;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
