package com.luke.fcmanagement.module.resource;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IResourceRepository extends JpaRepository<ResourceEntity, Long> {
    Optional<ResourceEntity> findResourceEntityByPath(String path);
}
