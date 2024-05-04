package com.luke.fcmanagement.module.resource.impl;

import com.luke.fcmanagement.config.LocalSaverFileConfig;
import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.module.resource.IResourceRepository;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.ResourceEntity;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.constant.TargetType;
import com.luke.fcmanagement.module.resource.file.FileUtils;
import com.luke.fcmanagement.module.resource.file.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements IResourceService {

    private final IResourceRepository resourceRepository;

    private final IFileService fileService;
    private final LocalSaverFileConfig localSaverFileConfig;

    @Override
    public void saveBathResource(List<MultipartFile> resources, Long targetId, TargetType targetType) {
        log.info("[START] save resource batch :{} files of target id: {}", resources.size(), targetId);
        if (CollectionUtils.isEmpty(resources)) return;
        for (MultipartFile resource : resources) {
            MediaType fcMediaType;
            if (FileUtils.isImage(resource)) {
                fcMediaType = MediaType.IMAGE;
            } else {
                fcMediaType = MediaType.VIDEO;
            }
            this.saveResource(resource, targetId, fcMediaType, targetType);
        }
        log.info("[END] save resource batch :{} files of target id: {}", resources.size(), targetId);
    }

    public void saveResource(MultipartFile resource, long targetId, MediaType fcMediaType, String fileName, TargetType targetType) {
        if (Objects.isNull(resource) || Objects.isNull(fcMediaType)) return;
        log.info("[START] save resource {} name: {}", fcMediaType.getDisplay(), fileName);
        String pathSave = this.fileService.saveFile(resource, fcMediaType, fileName);
        ResourceEntity logoFC = ResourceEntity
                .builder()
                .path(pathSave)
                .targetId(targetId)
                .mediaType(fcMediaType.getValue())
                .description(fcMediaType.getDisplay())
                .targetType(targetType.getValue())
                .build();
        resourceRepository.save(logoFC);
        log.info("[END] save resource {} name: {}", fcMediaType.getDisplay(), fileName);
    }

    @Override
    public void batchDeleteResourceById(List<Long> ids) {
        log.info("[START] delete fc resource batch :{} id files", ids.size());
        if (CollectionUtils.isEmpty(ids)) return;
        this.resourceRepository.deleteAllByIdInBatch(ids);
        log.info("[END] delete fc resource batch :{} id files", ids.size());
    }

    @Override
    public void deleteResource(String path) {
        log.info("[START] delete resource with path : {}", path.replace(this.localSaverFileConfig.getHost(), ""));
        String pathDelLocal = path.replace(this.localSaverFileConfig.getHost(), this.localSaverFileConfig.getAbsolutePath()).replace("/", File.separator);
        ResourceEntity resource = this.resourceRepository.findResourceEntityByPath(path).orElseThrow(() -> new BusinessException(ErrorCode.VALIDATE_FAIL));
        this.resourceRepository.delete(resource);
        this.fileService.deleteFile(pathDelLocal);
        log.info("[END] delete resource with path : {}", path.replace(this.localSaverFileConfig.getHost(), ""));
    }


}
