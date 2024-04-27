package com.luke.fcmanagement.module.member.impl;

import com.luke.fcmanagement.module.resource.constant.FCMediaType;
import com.luke.fcmanagement.constants.FCStatus;
import com.luke.fcmanagement.module.member.MemberEntity;
import com.luke.fcmanagement.module.resource.file.IFileService;
import com.luke.fcmanagement.module.football_club.request.CreateFCMemberRequest;
import com.luke.fcmanagement.module.member.IMemberRepository;
import com.luke.fcmanagement.module.member.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements IMemberService {

    private final IMemberRepository memberRepository;
    private final IFileService fileService;

    @Override
    public void saveFcMember(List<CreateFCMemberRequest> members, Long fcId) {
        if (CollectionUtils.isEmpty(members)) return;
        log.info("save fc member-{} fcId-{}", members.size(), fcId);
        members.forEach(e -> {
            MemberEntity member = e.toEntity(fcId, FCStatus.INACTIVE);
            MemberEntity saveMem = memberRepository.save(member);
            if (Objects.nonNull(e.getAvatar())) {
                String fileName = fcId + File.separator + "member" + File.separator + saveMem.getFcMemberId() + "_" + e.getAvatar().getOriginalFilename();
                String pathSave = fileService.saveFile(e.getAvatar(), FCMediaType.IMAGE, fileName);
                saveMem.setAvatar(pathSave);
                memberRepository.save(saveMem);
            }
        });
    }
}
