package com.luke.fcmanagement.module.resource.impl;

import com.luke.fcmanagement.config.LocalSaverFileConfig;
import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.module.job.IJobService;
import com.luke.fcmanagement.module.job.delete_resource.DeleteResourceJob;
import com.luke.fcmanagement.module.resource.IResourceRepository;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.ResourceEntity;
import com.luke.fcmanagement.module.resource.constant.KeyType;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.file.FileUtils;
import com.luke.fcmanagement.module.resource.file.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements IResourceService {

    private final IResourceRepository resourceRepository;
    private final IFileService fileService;
    private final LocalSaverFileConfig localSaverFileConfig;
    private final IJobService jobService;

    @Value("${media.saver.active}")
    private String provider;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveBathResource(List<MultipartFile> resources, Long targetId, KeyType targetType) {
        log.info("save resource batch :{} files of target id: {}", resources.size(), targetId);
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
    }

    @Transactional(rollbackFor = Throwable.class)
    public void saveResource(MultipartFile resource, long targetId, MediaType mediaType, String fileName, KeyType targetType) {
        if (Objects.isNull(resource) || Objects.isNull(mediaType)) return;
        log.info("save resource {} name: {}", mediaType.getDisplay(), fileName);
        String pathSave = this.fileService.saveFile(resource, mediaType, fileName);
        ResourceEntity logoFC = ResourceEntity
                .builder()
                .path(pathSave)
                .keyId(targetId)
                .mediaType(mediaType.getValue())
                .description(mediaType.getDisplay())
                .keyType(targetType.getValue())
                .provider(provider)
                .build();
        resourceRepository.save(logoFC);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteResource(String path) {
        log.info("delete resource with path : {}", path.replace(this.localSaverFileConfig.getHost(), ""));
        String pathDelLocal = path.replace(this.localSaverFileConfig.getHost(), this.localSaverFileConfig.getAbsolutePath()).replace("/", File.separator);
        ResourceEntity resource = this.resourceRepository.findResourceEntityByPath(path).orElseThrow(() -> new BusinessException(ErrorCode.VALIDATE_FAIL));
        this.resourceRepository.delete(resource);
        DeleteResourceJob deleteResourceJob = DeleteResourceJob.builder()
                .path(pathDelLocal)
                .build();
        this.jobService.createJob(deleteResourceJob);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteResourcesByTargetIdsAndTargetType(List<Long> targetIds, KeyType targetType) {
        log.info("delete resource by list targetIds size: {} and target type :{}", targetIds.size(), targetType.getDisplay());
        List<ResourceEntity> list = resourceRepository.findResourceEntitiesByKeyIdInAndKeyType(targetIds, targetType.getValue());
        List<String> paths = list.stream().map(ResourceEntity::getPath).toList();
        Stream.ofNullable(paths).flatMap(Collection::stream).forEach(p -> {
                    String pathDelLocal = p.replace(this.localSaverFileConfig.getHost(), this.localSaverFileConfig.getAbsolutePath()).replace("/", File.separator);
                    DeleteResourceJob deleteResourceJob = DeleteResourceJob.builder()
                            .path(pathDelLocal)
                            .build();
                    this.jobService.createJob(deleteResourceJob);
                }
        );
    }

    @Override
    public List<String> findResourcesByKeyIdAndKeyType(Long keyId, Integer keyType) {
        log.info("get resource by keyId: {}", keyId);
        return this.resourceRepository.findByKeyId(keyId, keyType);
    }

    @Override
    public String getPathByKeyTypeAndKeyIdAndMediaType(Integer keyType, Long keyId, String mediaType) {
        log.info("get path by keyId: {} - keyType: {} - mediaType: {}", keyId, keyType, mediaType);
        return null;
    }


}
