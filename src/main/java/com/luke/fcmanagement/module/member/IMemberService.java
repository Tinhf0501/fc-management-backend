package com.luke.fcmanagement.module.member;

import com.luke.fcmanagement.module.football_club.request.CreateFCMemberRequest;

import java.util.List;

public interface IMemberService {

    void saveFcMember(List<CreateFCMemberRequest> members, Long fcId);
}
