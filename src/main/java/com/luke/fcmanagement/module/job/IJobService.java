package com.luke.fcmanagement.module.job;

import com.luke.fcmanagement.constants.JobType;

import java.util.List;

public interface IJobService {
    void createJob(String jobValue, JobType jobType);
}
