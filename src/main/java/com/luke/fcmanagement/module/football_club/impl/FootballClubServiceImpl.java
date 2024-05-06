package com.luke.fcmanagement.module.football_club.impl;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.constants.FCStatus;
import com.luke.fcmanagement.constants.Message;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiBody;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.FootballClubEntity;
import com.luke.fcmanagement.module.football_club.IFootballClubRepository;
import com.luke.fcmanagement.module.football_club.IFootballClubService;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.request.UpdateFCRequest;
import com.luke.fcmanagement.module.member.IMemberService;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.constant.MediaType;
import com.luke.fcmanagement.module.resource.constant.TargetType;
import com.luke.fcmanagement.utils.JSON;
import com.luke.fcmanagement.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FootballClubServiceImpl implements IFootballClubService {
    private final IFootballClubRepository footballClubRepository;

    private final IMemberService memberService;
    private final IResourceService resourceService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResponse createFC(CreateFCRequest request, BindingResult bindingResult) throws BindException {
        log.info("API : {} send REQUEST : body-{}", Utils.getRequestUri(), JSON.stringify(request));
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        // * save FC
        FootballClubEntity fc = FootballClubEntity.builder()
                .fcName(request.getFcName())
                .description(request.getDescription())
                .status(FCStatus.INACTIVE.getValue())
                .build();
        FootballClubEntity fcSaved = footballClubRepository.save(fc);

        // * save FC Member
        this.memberService.saveFcMember(request.getFcMembers(), fcSaved.getFcId());

        // * save logo FC
        Optional.ofNullable(request.getLogo())
                .ifPresent(logo -> this.resourceService.saveResource(logo, fcSaved.getFcId(), MediaType.IMAGE, TargetType.FC));

        // * save media FC
        Optional.ofNullable(request.getMedia()).ifPresent(medias -> this.resourceService.saveBathResource(medias, fcSaved.getFcId(), TargetType.FC));

        ApiBody apiBody = new ApiBody();
        apiBody.setMessage(Message.CREATE_FC_SUCCESS);
        return ApiResponse.ok(apiBody);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResponse updateFC(UpdateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException {
        log.info("API : {} send REQUEST : body-{}", Utils.getRequestUri(), JSON.stringify(request));
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        FootballClubEntity entity = footballClubRepository.findById(request.getFcId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_RECORD));
        entity.setFcName(request.getFcName());
        entity.setDescription(request.getDescription());
        FootballClubEntity fcSaved = footballClubRepository.save(entity);

        // * save FC Member
        this.memberService.saveFcMember(request.getFcMembers(), fcSaved.getFcId());

        // update fc member
        this.memberService.updateMember(request.getFcMemberUpdate(), fcSaved.getFcId());

        // * save logo FC
        if (Objects.nonNull(request.getLogo()) && Objects.isNull(request.getPathLogoDel()))
            throw new BusinessException(ErrorCode.VALIDATE_FAIL);
        Optional.ofNullable(request.getLogo())
                .ifPresent(logo -> this.resourceService.saveResource(logo, fcSaved.getFcId(), MediaType.IMAGE, TargetType.FC));

        // * xóa logo cũ
        Optional.ofNullable(request.getPathLogoDel())
                .ifPresent(logo -> this.resourceService.deleteResource(logo));

        // * xóa list member
        Optional.ofNullable(request.getFcMemberIdsDelete()).ifPresent(memberId -> this.memberService.batchDeleteFcMemberById(memberId));

        // * xóa list media
        Stream.ofNullable(request.getPathMediaDelete()).flatMap(Collection::stream).forEach(path -> this.resourceService.deleteResource(path));

        ApiBody apiBody = new ApiBody();
        apiBody.setMessage(Message.UPDATE_FC_SUCCESS);
        return ApiResponse.ok(apiBody);
    }
}