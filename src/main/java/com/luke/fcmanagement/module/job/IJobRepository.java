package com.luke.fcmanagement.module.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IJobRepository extends JpaRepository<JobEntity, Long> {
    @Query(value = "select p from JobEntity p where p.jobType = :jobType and (p.status is null or p.status = :status)")
    List<JobEntity> findAllByJobType(@Param("jobType") String jobType, @Param("status") String status);
}
