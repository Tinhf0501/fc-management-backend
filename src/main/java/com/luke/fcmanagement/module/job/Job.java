package com.luke.fcmanagement.module.job;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Job {
    @JsonIgnore
    JobType getType();
}
