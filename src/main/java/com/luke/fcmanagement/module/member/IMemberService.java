package com.luke.fcmanagement.module.member;

import com.luke.fcmanagement.module.member.request.CreateFCMemberRequest;
import com.luke.fcmanagement.module.member.request.UpdateFCMemberRequest;
import com.luke.fcmanagement.module.member.response.DetailMemberResponse;

import java.util.List;

public interface IMemberService {

    void saveFcMember(List<CreateFCMemberRequest> members, Long fcId);

    void batchDeleteFcMemberById(List<Long> memberIds);

    void updateMember(List<UpdateFCMemberRequest> members);

    List<DetailMemberResponse> findAllByFcId(Long fcId);
}
