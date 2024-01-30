package com.luke.fcmanagement.repository;

import com.luke.fcmanagement.entity.FundFCMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundFcMemberRepository extends JpaRepository<FundFCMemberEntity, Long> {
}
