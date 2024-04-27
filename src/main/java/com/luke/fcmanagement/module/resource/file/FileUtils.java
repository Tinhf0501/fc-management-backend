package com.luke.fcmanagement.module.resource.file;


import com.luke.fcmanagement.module.resource.constant.ResourceConstant;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@UtilityClass
public class FileUtils {

    public boolean isMP4(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && fileName.toLowerCase().endsWith(".mp4");
    }


    public boolean isImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return StringUtils.isNotBlank(fileName) && ResourceConstant.EXTENSION_IMAGE.stream()
                .anyMatch(fileName::endsWith);
    }


    public boolean isValidListFile(List<MultipartFile> files) {
        return Stream.ofNullable(files)
                .flatMap(Collection::stream)
                .allMatch(file -> isMP4(file) || isImage(file));
    }
}
