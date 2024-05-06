package com.luke.fcmanagement.module.member.impl;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.constants.FCStatus;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.module.football_club.request.CreateFCMemberRequest;
import com.luke.fcmanagement.module.football_club.request.UpdateFCMemberRequest;
import com.luke.fcmanagement.module.member.IMemberRepository;
import com.luke.fcmanagement.module.member.IMemberService;
import com.luke.fcmanagement.module.member.MemberEntity;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.constant.TargetType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements IMemberService {

    private final IMemberRepository memberRepository;
    private final IResourceService resourceService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveFcMember(List<CreateFCMemberRequest> members, Long fcId) {
        if (CollectionUtils.isEmpty(members)) return;
        log.info("save fc member-{} fcId-{}", members.size(), fcId);
        members.forEach(e -> {
            MemberEntity member = e.toEntity(fcId, FCStatus.INACTIVE);
            MemberEntity saveMem = this.memberRepository.save(member);
            if (Objects.nonNull(e.getAvatar())) {
                this.resourceService.saveResource(e.getAvatar(), saveMem.getFcMemberId(), MediaType.IMAGE, TargetType.MEMBER);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void batchDeleteFcMemberById(List<Long> memberIds) {
        if (CollectionUtils.isEmpty(memberIds)) return;
        log.info("delete fc member in batch by list ids size : {}", memberIds.size());
        this.memberRepository.deleteAllByIdInBatch(memberIds);
        this.resourceService.deleteResourcesByTargetIdsAndTargetType(memberIds, TargetType.MEMBER);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateMember(List<UpdateFCMemberRequest> members, Long fcId) {
        if (CollectionUtils.isEmpty(members)) return;
        log.info("save fc member-{} fcId-{}", members.size(), fcId);
        members.forEach(e -> {
            MemberEntity memberOnDb = this.memberRepository.findById(e.getMemberId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RECORD));
            if (Objects.equals(memberOnDb.getFcId(), e.getFcId()))
                throw new BusinessException(ErrorCode.VALIDATE_FAIL);
            MemberEntity member = e.toEntity(e.getFcId(), FCStatus.getStatus(memberOnDb.getStatus()));
            member.setFcMemberId(e.getMemberId());
            member.setCreatedBy(memberOnDb.getCreatedBy());
            member.setCreatedDate(memberOnDb.getCreatedDate());
            if (Objects.nonNull(e.getAvatar()) && Objects.isNull(e.getPathAvatarDel()))
                throw new BusinessException(ErrorCode.VALIDATE_FAIL);
            Optional.ofNullable(e.getAvatar())
                    .ifPresent(
                            avt -> this.resourceService.saveResource(e.getAvatar(), member.getFcMemberId(), MediaType.IMAGE, TargetType.MEMBER)
                    );
            Optional.ofNullable(e.getPathAvatarDel())
                    .ifPresent(
                            path -> this.resourceService.deleteResource(path)
                    );
            this.memberRepository.save(member);
        });
    }
}
