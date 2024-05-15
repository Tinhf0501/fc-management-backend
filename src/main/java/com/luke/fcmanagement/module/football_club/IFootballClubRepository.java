package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.module.football_club.request.SearchFcRequest;
import com.luke.fcmanagement.module.football_club.response.DetailFCResponse;
import com.luke.fcmanagement.module.football_club.response.ISearchFCResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IFootballClubRepository extends JpaRepository<FootballClubEntity, Long> {

    @Query(value = "select f.fcId as fcId, " +
            "f.fcName as fcName, " +
            "f.description as desc, " +
            "f.createdDate as createdDate, " +
            "f.createdBy as createdBy, " +
            "f.updatedDate as updatedDate, " +
            "f.updatedBy as updatedBy, " +
            "f.status as status, " +
            "f.slug as slug, " +
            "(select count(m.memberId) from MemberEntity m where m.fcId = f.fcId) as totalMembers" +
            " from FootballClubEntity f " +
            "where (:#{#params.fcName} is null or f.fcName like concat('%',:#{#params.fcName},'%')) " +
            "and (:#{#params.fcStatus} is null or f.status = :#{#params.fcStatus}) " +
            "and (:#{#params.fromDate} is null or f.createdDate >= :#{#params.fromDate}) " +
            "and (:#{#params.toDate} is null or f.createdDate <= :#{#params.toDate}) " +
            "order by f.createdDate desc")
    Page<ISearchFCResponse> search(
            @Param("params") SearchFcRequest request,
            Pageable pageable
    );

    @Query(value = "select new com.luke.fcmanagement.module.football_club.response.DetailFCResponse(f.fcId,f.fcName,f.description,f.status,f.slug,f.createdDate,r.path) " +
            "from FootballClubEntity f " +
            "inner join ResourceEntity r on f.fcId=r.keyId " +
            "where f.fcId=:fcId and r.keyType=:keyType and r.mediaType=:mediaType")
    DetailFCResponse findDetailFcByFcIdAndKeyTypeAndMediaType(@Param("fcId") Long fcId, @Param("keyType") Integer keyType, @Param("mediaType") String mediaType);
}
