package com.luke.fcmanagement.module.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobRepository extends JpaRepository<JobEntity, Long> {
}
