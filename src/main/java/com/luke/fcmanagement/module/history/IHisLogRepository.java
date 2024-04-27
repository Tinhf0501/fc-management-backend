package com.luke.fcmanagement.module.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IHisLogRepository extends JpaRepository<HisLogEntity, Long> {
}
