package com.luke.fcmanagement.module.football_club.impl;

import com.luke.fcmanagement.constants.*;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiBody;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.FootballClubEntity;
import com.luke.fcmanagement.module.football_club.IFootballClubRepository;
import com.luke.fcmanagement.module.football_club.IFootballClubService;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.request.CreateFCResourceRequest;
import com.luke.fcmanagement.module.member.IMemberService;
import com.luke.fcmanagement.module.resource.IResourceService;
import com.luke.fcmanagement.module.resource.constant.FCMediaType;

import com.luke.fcmanagement.utils.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.Optional;

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
        log.info("REQUEST : body-{}", JSON.stringify(request));
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        if (this.isValidGuestFC(request)) {
            throw new BusinessException(ErrorCode.VALIDATE_FAIL);
        }
        // * save FC
        FootballClubEntity fc = FootballClubEntity.builder()
                .fcName(request.getFcName())
                .description(request.getDescription())
                .status(FCStatus.INACTIVE.getValue())
                .isGuest(request.getIsGuest())
                .build();
        FootballClubEntity fcSaved = footballClubRepository.save(fc);

        // * save FC Member
        this.memberService.saveFcMember(request.getFcMembers(), fcSaved.getFcId());

        // * save logo FC
        Optional.ofNullable(request.getFcResources())
                .map(CreateFCResourceRequest::getLogo)
                .ifPresent(logo -> {
                    this.resourceService.saveResource(logo, fcSaved.getFcId(), FCMediaType.IMAGE);
                });

        // * save media FC
        Optional.ofNullable(request.getFcResources())
                .map(CreateFCResourceRequest::getMedia)
                .ifPresent(medias -> {
                    this.resourceService.saveBathResource(medias, fcSaved.getFcId());
                });

        ApiBody apiBody = new ApiBody();
        apiBody.setMessage(Message.CREATE_FC_SUCCESS);
        return ApiResponse.ok(apiBody);
    }


    private boolean isValidGuestFC(CreateFCRequest request) {
        boolean isGuest = Boolean.TRUE.equals(request.getIsGuest());
        boolean isValidGuestFCMembers = CollectionUtils.isEmpty(request.getFcMembers());
        boolean isValidGuestFcMedia = Optional.ofNullable(request.getFcResources())
                .map(CreateFCResourceRequest::getMedia)
                .map(CollectionUtils::isEmpty)
                .orElse(true);
        return isGuest && isValidGuestFCMembers && isValidGuestFcMedia;
    }
}