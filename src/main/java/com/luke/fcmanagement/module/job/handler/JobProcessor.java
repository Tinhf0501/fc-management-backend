package com.luke.fcmanagement.module.job.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luke.fcmanagement.constants.AppConstants;
import com.luke.fcmanagement.constants.Status;
import com.luke.fcmanagement.module.job.IJobRepository;
import com.luke.fcmanagement.module.job.JobEntity;
import com.luke.fcmanagement.module.job.constants.JobType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class JobProcessor {
    @Setter(onMethod_ = {@Autowired})
    private IJobRepository jobRepository;

    protected abstract JobType initJobTye();

    public List<JobEntity> initDataJob() {
        JobType jobType = initJobTye();
        return jobRepository.findAllByJobType(jobType.getValue(), Status.FAIL.getValue());
    }

    protected abstract void processJob(JobEntity jobEntity) throws JsonProcessingException;

    protected abstract void processEntity(List<JobEntity> jobEntityList);

    public void execute() {
        List<JobEntity> entityList = initDataJob();
        log.info("List job records in Db size = {}",entityList.size());
        entityList.forEach(jobEntity -> {
            String traceId = UUID.randomUUID().toString();
            MDC.put(AppConstants.TRACE_ID_KEY, traceId);
            jobEntity.setTraceId(traceId);
            try {
                log.info("Job: value = {}, type = {}", jobEntity.getJobValue(), jobEntity.getJobType());
                processJob(jobEntity);
                jobEntity.setStatus(Status.SUCCESS.getCode());
                jobEntity.setMessage(Status.SUCCESS.getValue());
            } catch (Exception e) {
                jobEntity.setStatus(Status.FAIL.getValue());
                jobEntity.setMessage(StringUtils.substring(e.getMessage(), 0, 255));
            } finally {
                MDC.clear();
            }
        });
        processEntity(entityList);
    }
}
