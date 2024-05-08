package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.module.football_club.response.ISearchFCResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

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
            "(select count(m.fcMemberId) from MemberEntity m where m.fcId = f.fcId) as totalMembers" +
            " from FootballClubEntity f " +
            "where (:fcName is null or f.fcName like concat('%',:fcName,'%')) " +
            "and (:fcStatus is null or f.status = :fcStatus) " +
            "and (:fromDate is null or f.createdDate>=:fromDate) " +
            "and (:toDate is null or f.createdDate <=:toDate) " +
            "order by f.createdDate desc")
    Page<ISearchFCResponse> search(
            @Param("fcName") String fcName,
            @Param("fcStatus") Integer fcStatus,
            @Param("fromDate") Date createdDate,
            @Param("toDate") Date toDate,
            Pageable pageable
    );
}
