package com.luke.fcmanagement.module.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IResourceRepository extends JpaRepository<ResourceEntity, Long> {
    Optional<ResourceEntity> findResourceEntityByPath(String path);

    List<ResourceEntity> findResourceEntitiesByKeyIdInAndKeyType(List<Long> ids, Integer targetType);

    @Query(value = "select r.path " +
            "from ResourceEntity r where r.keyId=:keyId and r.keyType=:keyType")
    List<String> findByKeyIdAndKeyType(@Param("keyId") Long keyId, @Param("keyType") Integer keyType);

    List<ResourceEntity> findResourceEntitiesByKeyIdAndKeyTypeAndMediaType(Long keyId, Integer keyType, String mediaType);

    @Query(value = "select r.path from ResourceEntity r")
    List<String> getAllPath();
}
