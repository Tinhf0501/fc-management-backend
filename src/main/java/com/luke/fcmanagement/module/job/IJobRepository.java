package com.luke.fcmanagement.module.job;

import com.luke.fcmanagement.module.job.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobRepository extends JpaRepository<JobEntity, Long> {
}
