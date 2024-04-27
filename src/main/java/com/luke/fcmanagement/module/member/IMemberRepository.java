package com.luke.fcmanagement.module.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IMemberRepository extends JpaRepository<MemberEntity, Long> {
}
