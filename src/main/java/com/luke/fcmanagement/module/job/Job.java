package com.luke.fcmanagement.module.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luke.fcmanagement.module.job.constants.JobType;

public interface Job {
    @JsonIgnore
    JobType getType();
}
