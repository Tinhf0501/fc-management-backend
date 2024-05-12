package com.luke.fcmanagement.module.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMemberRepository extends JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findAllByFcMemberIdIn(List<Long> ids);
}
