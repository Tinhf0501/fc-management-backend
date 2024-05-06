package com.luke.fcmanagement.module.job;

import com.luke.fcmanagement.constants.JobType;

public interface IJobService {
    void createJob(String jobValue, JobType jobType);
}
