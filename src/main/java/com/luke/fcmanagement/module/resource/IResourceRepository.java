package com.luke.fcmanagement.module.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IResourceRepository extends JpaRepository<ResourceEntity, Long> {
}
