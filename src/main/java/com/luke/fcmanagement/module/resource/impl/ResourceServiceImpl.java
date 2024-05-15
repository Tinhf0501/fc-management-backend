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
    public void saveResource(MultipartFile resource, Long targetId, MediaType mediaType, String fileName, KeyType targetType) {
        if (Objects.isNull(resource)) return;
        if (Objects.isNull(targetId) || Objects.isNull(targetType) || Objects.isNull(mediaType))
            throw new BusinessException(ErrorCode.VALIDATE_FAIL);
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
    public void deleteResourceByPath(String path) {
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
        deleteListReSources(list);
    }

    @Override
    public List<String> findResourcesByKeyIdAndKeyType(Long keyId, Integer keyType) {
        log.info("get resource by keyId: {}", keyId);
        return this.resourceRepository.findByKeyIdAndKeyType(keyId, keyType);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteResourcesByKeyIdAndKeyTypeAndMediaType(Long keyId, Integer keyType, String mediaType) {
        log.info("delete resource by keyId: {} - key type :{} - mediaType:{}", keyId, keyType, mediaType);
        List<ResourceEntity> list = this.resourceRepository.findResourceEntitiesByKeyIdAndKeyTypeAndMediaType(keyId, keyType, mediaType);
        deleteListReSources(list);
    }

    private void deleteListReSources(List<ResourceEntity> list){
        if (list.isEmpty())
            return;
        List<String> paths = list.stream().map(ResourceEntity::getPath).toList();
        this.resourceRepository.deleteAllInBatch(list);
        Stream.ofNullable(paths).flatMap(Collection::stream).forEach(p -> {
                    String pathDelLocal = p.replace(this.localSaverFileConfig.getHost(), this.localSaverFileConfig.getAbsolutePath()).replace("/", File.separator);
                    DeleteResourceJob deleteResourceJob = DeleteResourceJob.builder()
                            .path(pathDelLocal)
                            .build();
                    this.jobService.createJob(deleteResourceJob);
                }
        );
    }

}
