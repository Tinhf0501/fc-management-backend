package com.luke.fcmanagement.module.resource.file.impl;

import com.luke.fcmanagement.config.LocalSaverFileConfig;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.file.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@ConditionalOnBean(LocalSaverFileConfig.class)
@RequiredArgsConstructor
public class LocalFileService implements IFileService {

    private final LocalSaverFileConfig localSaverFileConfig;
    private final Environment environment;

    @Override
    public String saveFile(MultipartFile file, MediaType fcMediaType, String fileName) {
        try {
            String folderPath = this.environment.getProperty(fcMediaType.getFolderEnvKey());
            String pathSave = folderPath + fileName;
            final File f = new File(this.localSaverFileConfig.getAbsolutePath() + pathSave);
            if (!f.exists()) {
                f.mkdirs();
            }
            file.transferTo(f);
            return this.localSaverFileConfig.getHost() + folderPath + fileName.replace(File.separator, "/");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
