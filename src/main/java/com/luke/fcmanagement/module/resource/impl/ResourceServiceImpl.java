package com.luke.fcmanagement.module.resource.impl;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.module.resource.constant.FCMediaType;
import com.luke.fcmanagement.module.resource.ResourceEntity;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.module.resource.file.FileUtils;
import com.luke.fcmanagement.module.resource.file.IFileService;
import com.luke.fcmanagement.module.resource.IResourceRepository;
import com.luke.fcmanagement.module.resource.IResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements IResourceService {

    private final IResourceRepository resourceRepository;

    private final IFileService fileService;

    @Override
    public void saveBathResource(List<MultipartFile> resources, Long fcId) {
        if (CollectionUtils.isEmpty(resources)) return;
        if (!FileUtils.isValidListFile(resources)) {
            throw new BusinessException(ErrorCode.VALIDATE_FAIL);
        }
        for (MultipartFile resource : resources) {
            FCMediaType fcMediaType;
            if (FileUtils.isImage(resource)) {
                fcMediaType = FCMediaType.IMAGE;
            } else {
                fcMediaType = FCMediaType.VIDEO;
            }
            this.saveResource(resource, fcId, fcMediaType);
        }
    }

    public void saveResource(MultipartFile resource, long fcId, FCMediaType fcMediaType, String fileName) {
        if (Objects.isNull(resource) || Objects.isNull(fcMediaType)) return;
        log.info("save resource {} name: {}", fcMediaType.getDisplay(), fileName);
        String pathSave = this.fileService.saveFile(resource, fcMediaType, fileName);
        ResourceEntity logoFC = ResourceEntity
                .builder()
                .path(pathSave)
                .fcId(fcId)
                .type(fcMediaType.getValue())
                .description(fcMediaType.getDisplay())
                .build();
        resourceRepository.save(logoFC);
    }
}
