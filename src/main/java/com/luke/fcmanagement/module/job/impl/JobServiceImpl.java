package com.luke.fcmanagement.module.job.impl;

import com.luke.fcmanagement.module.job.IJobRepository;
import com.luke.fcmanagement.module.job.IJobService;
import com.luke.fcmanagement.module.job.Job;
import com.luke.fcmanagement.module.job.JobEntity;
import com.luke.fcmanagement.utils.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class JobServiceImpl implements IJobService {
    private final IJobRepository jobRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createJob(Job jobValue) {
        log.info("create job entity with job type: {}", jobValue.getType().getDesc());
        JobEntity jobEntity = JobEntity.builder()
                .jobType(jobValue.getType().getValue())
                .jobValue(JSON.writeObject(jobValue))
                .build();
        this.jobRepository.save(jobEntity);
    }
}
