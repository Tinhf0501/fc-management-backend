package com.luke.fcmanagement.module.job;

import com.luke.fcmanagement.constants.JobType;

public interface IJobService {
    void create(String jobValue, JobType jobType);
}
