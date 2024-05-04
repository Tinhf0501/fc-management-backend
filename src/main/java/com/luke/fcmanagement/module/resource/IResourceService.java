package com.luke.fcmanagement.module.resource;

import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.constant.TargetType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IResourceService {

    void saveBathResource(List<MultipartFile> resources, Long fcId, TargetType targetType);

    void saveResource(MultipartFile resource, long targetId, MediaType fcMediaType, String fileName, TargetType targetType);

    void batchDeleteResourceById(List<Long> ids);

    void deleteResource(String path);

    default void saveResource(MultipartFile resource, long targetId, MediaType fcMediaType, TargetType targetType) {
        String fileName = targetId + File.separator + resource.getOriginalFilename();
        this.saveResource(resource, targetId, fcMediaType, fileName, targetType);
    }
}
