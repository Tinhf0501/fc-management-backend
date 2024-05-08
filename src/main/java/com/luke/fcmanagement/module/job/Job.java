package com.luke.fcmanagement.module.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luke.fcmanagement.constants.JobType;

public interface Job {
    @JsonIgnore
    JobType getType();
}
