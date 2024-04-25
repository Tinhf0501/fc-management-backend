package com.luke.fcmanagement.module.football_club.impl;

import com.luke.fcmanagement.config.AppProperties;
import com.luke.fcmanagement.constants.*;
import com.luke.fcmanagement.entity.FCResourceEntity;
import com.luke.fcmanagement.entity.FootBallClubMemberEntity;
import com.luke.fcmanagement.entity.FootballClubEntity;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiBody;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.file.FileSaver;
import com.luke.fcmanagement.module.file.FileUtils;
import com.luke.fcmanagement.module.football_club.IFootballClubRepository;
import com.luke.fcmanagement.module.football_club.IFootballClubService;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import com.luke.fcmanagement.repository.FCResourceRepository;
import com.luke.fcmanagement.repository.FootballClubMemberRepository;
import com.luke.fcmanagement.utils.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FootballClubServiceImpl implements IFootballClubService {
    private final IFootballClubRepository footballClubRepository;
    private final FootballClubMemberRepository footballClubMemberRepository;
    private final FCResourceRepository fcResourceRepository;
    private final FileSaver fileSaver;
    private final AppProperties appProperties;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ApiResponse createFC(CreateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException {
        log.info("REQUEST : body-{}", JSON.stringify(request));
        System.out.println(appProperties.toString());
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
        this.saveFcMember(request, fcSaved.getFcId());


        // * save logo FC
        MultipartFile logo = request.getFcResources().getLogo();
        if (Objects.nonNull(logo)) {
            String fileName = fcSaved.getFcId() + "_" + logo.getOriginalFilename();
            String pathSave = fileSaver.saveFile(logo, appProperties.getFcLogoPath(), fileName);
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
        List<MultipartFile> media = request.getFcResources().getMedia();
        this.saveFcMedia(media, fcSaved.getFcId());

        ApiBody apiBody = new ApiBody();
        apiBody.put(FieldConstant.MESSAGE, Message.CREATE_FC_SUCCESS);
        return ApiResponse.ok(apiBody);
    }

    private void saveFcMember(CreateFCRequest request, Long fcId) {
        if (Objects.nonNull(request.getFcMembers())) {
            request.getFcMembers().stream().forEach(e -> {
                FootBallClubMemberEntity member = FootBallClubMemberEntity
                        .builder()
                        .userId(e.getUserId())
                        .fcId(fcId)
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
                    String pathSave = fileSaver.saveFile(e.getAvatar(), appProperties.getMemberPath(), fileName);
                    saveMem.setAvatar(pathSave);
                    footballClubMemberRepository.save(saveMem);
                }
            });
        }
    }

    private void saveFcMedia(List<MultipartFile> media, Long fcId) throws BusinessException {
        if (Objects.nonNull(media) && !media.isEmpty()) {
            if (!FileUtils.isValidListFile(media)) {
                throw new BusinessException(ErrorCode.VALIDATE_FAIL);
            } else {
                for (MultipartFile file : media) {
                    String fileName = fcId + "_" + file.getOriginalFilename();
                    String pathSave;
                    String type;
                    String desc;
                    if (FileUtils.isJPG(file)) {
                        pathSave = fileSaver.saveFile(file, appProperties.getFcImgPath(), fileName);
                        type = FCMediaType.IMAGE.getValue();
                        desc = FCMediaType.IMAGE.getDisplay();
                    } else {
                        pathSave = fileSaver.saveFile(file, appProperties.getFcVideoPath(), fileName);
                        type = FCMediaType.VIDEO.getValue();
                        desc = FCMediaType.VIDEO.getDisplay();
                    }

                    FCResourceEntity mediaFC = FCResourceEntity
                            .builder()
                            .path(pathSave)
                            .fcId(fcId)
                            .type(type)
                            .description(desc)
                            .build();
                    fcResourceRepository.save(mediaFC);
                }
            }
        }
    }

    private boolean isValidGuestFC(CreateFCRequest request) {
        boolean isGuest = Boolean.TRUE.equals(request.getIsGuest());
        boolean isValidGuestFCMembers = Objects.isNull(request.getFcMembers()) || request.getFcMembers().isEmpty();
        boolean isValidGuestFcMedia = Objects.isNull(request.getFcResources()) || Objects.isNull(request.getFcResources().getMedia()) || request.getFcResources().getMedia().isEmpty();
        return isGuest && isValidGuestFCMembers && isValidGuestFcMedia;
    }


}

