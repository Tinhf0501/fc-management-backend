package com.luke.fcmanagement.module.job.handler.impl.delete_resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.exception.JobProcessException;
import com.luke.fcmanagement.module.job.IJobRepository;
import com.luke.fcmanagement.module.job.JobEntity;
import com.luke.fcmanagement.module.job.constants.JobType;
import com.luke.fcmanagement.module.job.handler.JobProcessor;
import com.luke.fcmanagement.module.resource.file.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "job.delete-resource", value = "active", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class DeleteResourceJob extends JobProcessor {
    private final ObjectMapper mapper;
    private final IFileService fileService;
    private final IJobRepository jobRepository;

    @Override
    protected JobType initJobTye() {
        return JobType.DELETE_RESOURCE;
    }

    @Override
    protected void processJob(JobEntity jobEntity) throws JsonProcessingException {
        if (StringUtils.isBlank(jobEntity.getJobValue())) {
            throw new JobProcessException("Job value must be not null");
        }
        DeleteResource deleteResource = mapper.readValue(jobEntity.getJobValue(), DeleteResource.class);
        fileService.deleteFile(deleteResource.getPath());
    }

    @Override
    protected void processEntity(List<JobEntity> jobEntityList) {
        jobRepository.saveAll(jobEntityList);
    }

    @Scheduled(cron = "${job.delete-resource.time}")
    @Override
    public void execute() {
        log.info("[START] job delete resource");
        super.execute();
        log.info("[END] job delete resource");
    }
}
