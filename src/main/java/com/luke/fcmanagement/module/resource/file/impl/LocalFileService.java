package com.luke.fcmanagement.module.resource.file.impl;

import com.luke.fcmanagement.constants.FCMediaType;
import com.luke.fcmanagement.module.resource.file.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@ConditionalOnProperty(
        value = "file.save.type",
        havingValue = "LOCAL"
)
@RequiredArgsConstructor
public class LocalFileService implements IFileService {

    @Value("${media.absolute-path}")
    private String pathAbsolute;

    private final Environment environment;

    @Override
    public String saveFile(MultipartFile file, FCMediaType fcMediaType, String fileName) {
        try {
            String folderPath = this.environment.getProperty(fcMediaType.getFolderEnvKey());
            String pathSave = folderPath + fileName;
            final File f = new File(pathAbsolute + pathSave);
            if (!f.exists()) {
                f.mkdirs();
            }
            file.transferTo(f);
            return pathSave;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
