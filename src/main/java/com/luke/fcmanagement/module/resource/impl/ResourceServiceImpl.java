package com.luke.fcmanagement.module.resource.impl;

import com.luke.fcmanagement.config.LocalSaverFileConfig;
import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.module.job.IJobService;
import com.luke.fcmanagement.module.job.delete_resource.DeleteResourceJob;
import com.luke.fcmanagement.module.resource.IResourceRepository;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.ResourceEntity;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.constant.TargetType;
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
    public void saveBathResource(List<MultipartFile> resources, Long targetId, TargetType targetType) {
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
    public void saveResource(MultipartFile resource, long targetId, MediaType fcMediaType, String fileName, TargetType targetType) {
        if (Objects.isNull(resource) || Objects.isNull(fcMediaType)) return;
        log.info("save resource {} name: {}", fcMediaType.getDisplay(), fileName);
        String pathSave = this.fileService.saveFile(resource, fcMediaType, fileName);
        ResourceEntity logoFC = ResourceEntity
                .builder()
                .path(pathSave)
                .targetId(targetId)
                .mediaType(fcMediaType.getValue())
                .description(fcMediaType.getDisplay())
                .targetType(targetType.getValue())
                .provider(provider)
                .build();
        resourceRepository.save(logoFC);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void batchDeleteResourceById(List<Long> ids) {
        log.info("delete fc resource batch :{} id files", ids.size());
        if (CollectionUtils.isEmpty(ids)) return;
        this.resourceRepository.deleteAllByIdInBatch(ids);
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
    public void deleteResourcesByTargetIdsAndTargetType(List<Long> targetIds, TargetType targetType) {
        log.info("delete resource with by list targetIds size: {} and target type :{}", targetIds.size(), targetType.getDisplay());
        List<ResourceEntity> list = resourceRepository.findResourceEntitiesByTargetIdInAndTargetType(targetIds, targetType.getValue());
        List<String> paths = Stream.ofNullable(list).flatMap(Collection::stream).map(ResourceEntity::getPath).toList();
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
