package com.luke.fcmanagement.module.resource;

import com.luke.fcmanagement.module.resource.constant.FCMediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IResourceService {

    void saveBathResource(List<MultipartFile> resources, Long fcId);

    void saveResource(MultipartFile resource, long fcId, FCMediaType fcMediaType, String fileName);

    default void saveResource(MultipartFile resource, long fcId, FCMediaType fcMediaType) {
        String fileName = fcId + File.separator + resource.getOriginalFilename();
        this.saveResource(resource, fcId, fcMediaType, fileName);
    }
}
