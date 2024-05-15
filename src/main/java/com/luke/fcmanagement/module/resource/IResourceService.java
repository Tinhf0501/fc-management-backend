package com.luke.fcmanagement.module.resource;

import com.luke.fcmanagement.module.resource.constant.KeyType;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface IResourceService {

    void saveBathResource(List<MultipartFile> resources, Long fcId, KeyType targetType);

    void saveResource(MultipartFile resource, Long targetId, MediaType fcMediaType, String fileName, KeyType targetType);

    void deleteResourceByPath(String path);

    void deleteResourcesByTargetIdsAndTargetType(List<Long> targetIds, KeyType targetType);

    List<String> findResourcesByKeyIdAndKeyType(Long keyId, Integer keyType);

    void deleteResourcesByKeyIdAndKeyTypeAndMediaType(Long keyId, Integer keyType, String mediaType);

    default void saveResource(MultipartFile resource, Long targetId, MediaType fcMediaType, KeyType targetType) {
        String fileName = targetId + File.separator + resource.getOriginalFilename();
        this.saveResource(resource, targetId, fcMediaType, fileName, targetType);
    }
}
