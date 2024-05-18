package com.luke.fcmanagement.module.member;

import com.luke.fcmanagement.module.member.response.DetailMemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IMemberRepository extends JpaRepository<MemberEntity, Long> {
    @Query(value = "select new com.luke.fcmanagement.module.member.response.DetailMemberResponse(m.memberId,r.path,m.nameShirt,m.numberShirt,m.position,m.fullName,m.phoneNumber,m.address) " +
            "from MemberEntity m left join ResourceEntity r on m.memberId = r.keyId " +
            "where m.fcId=:fcId and r.keyType=:keyType")
    List<DetailMemberResponse> getAllMemberDetailByFcIdAndKeyType(@Param("fcId") Long fcId, @Param("keyType") Integer keyType);
}
