package com.luke.fcmanagement.repository;

import com.luke.fcmanagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
