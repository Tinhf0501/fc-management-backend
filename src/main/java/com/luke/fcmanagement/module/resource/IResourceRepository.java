package com.luke.fcmanagement.module.resource;

import com.luke.fcmanagement.entity.FCResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResourceRepository extends JpaRepository<FCResourceEntity, Long> {
}
