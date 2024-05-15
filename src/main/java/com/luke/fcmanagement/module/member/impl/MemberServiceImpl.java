package com.luke.fcmanagement.module.member.impl;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.module.football_club.FCStatus;
import com.luke.fcmanagement.module.member.IMemberRepository;
import com.luke.fcmanagement.module.member.IMemberService;
import com.luke.fcmanagement.module.member.MemberEntity;
import com.luke.fcmanagement.module.member.request.CreateFCMemberRequest;
import com.luke.fcmanagement.module.member.request.UpdateFCMemberRequest;
import com.luke.fcmanagement.module.member.response.DetailMemberResponse;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.constant.KeyType;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                this.resourceService.saveResource(e.getAvatar(), saveMem.getMemberId(), MediaType.IMAGE, KeyType.MEMBER);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void batchDeleteFcMemberById(List<Long> memberIds) {
        if (CollectionUtils.isEmpty(memberIds)) return;
        log.info("delete fc member in batch by list ids size : {}", memberIds.size());
        this.memberRepository.deleteAllByIdInBatch(memberIds);
        this.resourceService.deleteResourcesByTargetIdsAndTargetType(memberIds, KeyType.MEMBER);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateMember(List<UpdateFCMemberRequest> members) {
        if (CollectionUtils.isEmpty(members)) return;
        log.info("save fc member-{} fcId-{}", members.size());
        List<Long> listIds = members.stream().map(UpdateFCMemberRequest::getMemberId).toList();
        Map<Long, MemberEntity> listInDB = this.memberRepository.findAllById(listIds).stream()
                .collect(Collectors.toMap(MemberEntity::getMemberId, member -> member));
        members.forEach(e -> {
            MemberEntity memberOnDb = listInDB.get(e.getMemberId());
            if (Objects.isNull(memberOnDb))
                throw new BusinessException(ErrorCode.VALIDATE_FAIL);
            Long fcId = Objects.nonNull(e.getFcId()) ? e.getFcId() : memberOnDb.getFcId();
            MemberEntity member = e.toEntity(fcId, FCStatus.getStatus(memberOnDb.getStatus()));
            member.setMemberId(e.getMemberId());
            Optional.ofNullable(e.getAvatar()).ifPresent(
                    avt -> {
                        // * xóa avt cũ
                        this.resourceService.deleteResourcesByKeyIdAndKeyTypeAndMediaType(e.getMemberId(), KeyType.MEMBER.getValue(), MediaType.IMAGE.getValue());
                        this.resourceService.saveResource(e.getAvatar(), member.getMemberId(), MediaType.IMAGE, KeyType.MEMBER);
                    }
            );
            this.memberRepository.save(member);
        });
    }

    @Override
    public List<DetailMemberResponse> findAllByFcId(Long fcId) {
        log.info("get fc member details by fcId-{}", fcId);
        return this.memberRepository.getAllMemberDetailByFcIdAndKeyType(fcId, KeyType.MEMBER.getValue());
    }
}
