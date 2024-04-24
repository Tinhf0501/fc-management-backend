package com.luke.fcmanagement.module.football_club.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.fcmanagement.constants.*;
import com.luke.fcmanagement.entity.FCResourceEntity;
import com.luke.fcmanagement.entity.FootBallClubMemberEntity;
import com.luke.fcmanagement.entity.FootballClubEntity;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiBody;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.file.FileSaver;
import com.luke.fcmanagement.module.file.FileUtils;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.service.IFootballClubService;
import com.luke.fcmanagement.module.history.HistoryService;
import com.luke.fcmanagement.repository.FCResourceRepository;
import com.luke.fcmanagement.repository.FootballClubMemberRepository;
import com.luke.fcmanagement.repository.FootballClubRepository;
import com.luke.fcmanagement.utils.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FootballClubService implements IFootballClubService {
    private final FootballClubRepository footballClubRepository;
    private final FootballClubMemberRepository footballClubMemberRepository;
    private final FCResourceRepository fcResourceRepository;
    private final FileSaver fileSaver;
    private final HistoryService historyService;
    private final ObjectMapper objectMapper;

    @Value("${media.member.path}")
    private String memberPath;

    @Value("${media.fc.img-path}")
    private String fcImgPath;

    @Value("${media.fc.logo-path}")
    private String fcLogoPath;

    @Value("${media.fc.videos-path}")
    private String fcVideoPath;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResponse createFC(CreateFCRequest request) {
        log.info("REQUEST : body-{}", JSON.stringify(request));
        if (!isValidFC(request, request.getFcResources().getMedia())) {
            throw new BusinessException(ErrorCode.VALIDATE_FAIL);
        }
        MultipartFile[] media = request.getFcResources().getMedia();
        if (ArrayUtils.isNotEmpty(media)) {
            if (!FileUtils.isValidListFile(media)) {
                throw new BusinessException(ErrorCode.VALIDATE_FAIL);
            }
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
        if (Objects.nonNull(request.getFcMembers())) {
            request.getFcMembers().forEach(e -> {
                FootBallClubMemberEntity member = FootBallClubMemberEntity
                        .builder()
                        .userId(e.getUserId())
                        .fcId(fcSaved.getFcId())
                        .nameShirt(e.getNameShirt())
                        .numberShirt(e.getNumberShirt())
                        .fullName(e.getFullName())
                        .phoneNumber(e.getPhoneNumber())
                        .address(e.getAddress())
                        .description(e.getDescription())
                        .position(String.join(",", e.getPosition()))
                        .status(FCStatus.INACTIVE.getValue())
                        .build();
                FootBallClubMemberEntity saveMem = footballClubMemberRepository.save(member);
                if (Objects.nonNull(e.getAvatar())) {
                    String fileName = saveMem.getFcMemberId() + "_" + e.getAvatar().getOriginalFilename();
                    String pathSave = fileSaver.saveFile(e.getAvatar(), memberPath, fileName);
                    saveMem.setAvatar(pathSave);
                    footballClubMemberRepository.save(saveMem);
                }
            });
        }

        // * save logo FC
        MultipartFile logo = request.getFcResources().getLogo();
        if (Objects.nonNull(logo)) {
            String fileName = fcSaved.getFcId() + "_" + logo.getOriginalFilename();
            String pathSave = fileSaver.saveFile(logo, fcLogoPath, fileName);
            FCResourceEntity logoFC = FCResourceEntity
                    .builder()
                    .path(pathSave)
                    .fcId(fcSaved.getFcId())
                    .type(FCMediaType.LOGO.getValue())
                    .description(FCMediaType.LOGO.getDisplay())
                    .build();
            fcResourceRepository.save(logoFC);
        }

        // * save media FC
        if (ArrayUtils.isNotEmpty(media)) {
            for (MultipartFile file : media) {
                String fileName = fcSaved.getFcId() + "_" + file.getOriginalFilename();
                String pathSave;
                FCMediaType fcMediaType;
                if (FileUtils.isJPG(file)) {
                    pathSave = fileSaver.saveFile(file, fcImgPath, fileName);
                    fcMediaType = FCMediaType.IMAGE;
                } else {
                    pathSave = fileSaver.saveFile(file, fcVideoPath, fileName);
                    fcMediaType = FCMediaType.VIDEO;
                }

                FCResourceEntity mediaFC = FCResourceEntity
                        .builder()
                        .path(pathSave)
                        .fcId(fcSaved.getFcId())
                        .type(fcMediaType.getValue())
                        .description(fcMediaType.getDisplay())
                        .build();
                fcResourceRepository.save(mediaFC);
            }
        }

        // * save log
        this.historyService.saveHisLog(Status.SUCCESS.getStatus(), ActionType.CREATE_FC.getValue());

        ApiBody apiBody = new ApiBody();
        apiBody.put(FieldConstant.MESSAGE, Message.CREATE_FC_SUCCESS);
        return ApiResponse.ok(apiBody);
    }

    private boolean isValidFC(CreateFCRequest request, MultipartFile[] media) {
        if (Boolean.TRUE.equals(request.getIsGuest()) && (!request.getFcMembers().isEmpty() || media.length > 0))
            return false;
        return true;
    }
}
