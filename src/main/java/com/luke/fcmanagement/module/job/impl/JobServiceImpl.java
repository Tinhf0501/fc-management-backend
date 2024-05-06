package com.luke.fcmanagement.module.job.impl;

import com.luke.fcmanagement.constants.JobType;
import com.luke.fcmanagement.module.job.IJobRepository;
import com.luke.fcmanagement.module.job.IJobService;
import com.luke.fcmanagement.module.job.JobEntity;
import com.luke.fcmanagement.utils.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class JobServiceImpl implements IJobService {
    private final IJobRepository jobRepository;

    @Override
    public void create(String jobValue, JobType jobType) {
        JobEntity jobEntity = JobEntity.builder()
                .jobType(jobType.getValue())
                .jobValue(JSON.stringify(jobValue))
                .build();
        this.jobRepository.save(jobEntity);
    }
}
