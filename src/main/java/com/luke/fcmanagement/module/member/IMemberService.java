package com.luke.fcmanagement.module.member;

import com.luke.fcmanagement.module.football_club.request.CreateFCMemberRequest;
import com.luke.fcmanagement.module.football_club.request.UpdateFCMemberRequest;

import java.util.List;

public interface IMemberService {

    void saveFcMember(List<CreateFCMemberRequest> members, Long fcId);

    void batchDeleteFcMemberById(List<Long> memberIds);

    void updateMember(List<UpdateFCMemberRequest> members, Long fcId);
}
