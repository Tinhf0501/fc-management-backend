package com.luke.fcmanagement.module.job.handler.impl.delete_resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.constants.Status;
import com.luke.fcmanagement.exception.JobProcessException;
import com.luke.fcmanagement.module.job.JobEntity;
import com.luke.fcmanagement.module.job.constants.JobType;
import com.luke.fcmanagement.module.job.handler.JobProcessor;
import com.luke.fcmanagement.module.resource.file.IFileService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class DeleteResourceJob extends JobProcessor {
    private final ObjectMapper mapper;
    private final IFileService fileService;

    @Override
    protected JobType initJobTye() {
        return JobType.DELETE_RESOURCE;
    }

    @Override
    protected void processJob(JobEntity jobEntity) {
        if (StringUtils.isBlank(jobEntity.getJobValue())) {
            throw new JobProcessException("Job value must be not null");
        }
        DeleteResource deleteResource = mapper.convertValue(jobEntity.getJobValue(), DeleteResource.class);
        fileService.deleteFile(deleteResource.getPath());
    }
}
