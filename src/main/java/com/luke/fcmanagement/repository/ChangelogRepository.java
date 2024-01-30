package com.luke.fcmanagement.repository;

import com.luke.fcmanagement.entity.ChangeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangelogRepository extends JpaRepository<ChangeLogEntity, Long> {
}
