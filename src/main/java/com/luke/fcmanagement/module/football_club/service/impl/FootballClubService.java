package com.luke.fcmanagement.module.football_club.service.impl;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.constants.FCMediaType;
import com.luke.fcmanagement.constants.FCStatus;
import com.luke.fcmanagement.constants.StatusApi;
import com.luke.fcmanagement.entity.FCResourceEntity;
import com.luke.fcmanagement.entity.FootBallClubMemberEntity;
import com.luke.fcmanagement.entity.FootballClubEntity;
import com.luke.fcmanagement.model.ApiError;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.model.ErrorMsg;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.service.IFootballClubService;
import com.luke.fcmanagement.repository.FCResourceRepository;
import com.luke.fcmanagement.repository.FootballClubMemberRepository;
import com.luke.fcmanagement.repository.FootballClubRepository;
import com.luke.fcmanagement.utils.file_utils.FileChecker;
import com.luke.fcmanagement.utils.file_utils.FileSaver;
import com.luke.fcmanagement.utils.file_utils.FileSaverFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FootballClubService implements IFootballClubService {
    private final FootballClubRepository footballClubRepository;
    private final FootballClubMemberRepository footballClubMemberRepository;
    private final FCResourceRepository fcResourceRepository;

    @Value("${file.save.type}")
    private String saveFileType;

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
        ApiResponse response = new ApiResponse();
        if (!isValidFC(request)) {
            ApiError error = new ApiError();
            error.setErrorMsg(new ErrorMsg(ErrorCode.VALIDATE_FAIL.getMessage(), null));
            response.setCode(ErrorCode.VALIDATE_FAIL);
            response.setStatus(StatusApi.FAIL);
            return response;
        }
        // * save FC
        FootballClubEntity fc = FootballClubEntity.builder()
                .fcName(request.getFcName())
                .description(request.getDescription())
                .status(FCStatus.INACTIVE.getValue())
                .build();
        FootballClubEntity fcSaved = footballClubRepository.save(fc);

        // * get File Saver
        FileSaver fileSaver = FileSaverFactory.getFileSaver(saveFileType);

        // * save FC Member
        request.getFcMembers().stream().forEach(e -> {
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
                    .build();
            FootBallClubMemberEntity saveMem = footballClubMemberRepository.save(member);
            if (Objects.nonNull(e.getAvatar())) {
                String fileName = saveMem.getFcMemberId() + "_" + e.getAvatar().getOriginalFilename();
                String pathSave = fileSaver.saveFile(e.getAvatar(), memberPath, fileName);
                saveMem.setAvatar(pathSave);
                footballClubMemberRepository.save(saveMem);
            }
        });

        // * save resource
        if (Objects.nonNull(request.getFcResources().getLogo())) {
            String fileName = fcSaved.getFcId() + "_" + request.getFcResources().getLogo().getOriginalFilename();
            String pathSave = fileSaver.saveFile(request.getFcResources().getLogo(), fcLogoPath, fileName);
            FCResourceEntity logo = FCResourceEntity
                    .builder()
                    .path(pathSave)
                    .fcId(fcSaved.getFcId())
                    .type(FCMediaType.LOGO.getValue())
                    .description(FCMediaType.LOGO.getDisplay())
                    .build();
            fcResourceRepository.save(logo);
        }

        if (Objects.nonNull(request.getFcResources().getMedia()) && request.getFcResources().getMedia().length > 0) {
            if (!FileChecker.isValidListFile(request.getFcResources().getMedia())) {
                throw new RuntimeException("List resource files invalid");
            } else {
                for (MultipartFile file : request.getFcResources().getMedia()) {
                    String fileName = fcSaved.getFcId() + "_" + file.getOriginalFilename();
                    String pathSave;
                    String type;
                    String desc;
                    if (FileChecker.isJPG(file)) {
                        pathSave = fileSaver.saveFile(file, fcImgPath, fileName);
                        type = FCMediaType.LOGO.getValue();
                        desc = FCMediaType.LOGO.getDisplay();
                    } else {
                        pathSave = fileSaver.saveFile(file, fcVideoPath, fileName);
                        type = FCMediaType.VIDEO.getValue();
                        desc = FCMediaType.VIDEO.getDisplay();
                    }

                    FCResourceEntity logo = FCResourceEntity
                            .builder()
                            .path(pathSave)
                            .fcId(fcSaved.getFcId())
                            .type(type)
                            .description(desc)
                            .build();
                    fcResourceRepository.save(logo);
                }
            }
        }

        return null;
    }

    private boolean isValidFC(CreateFCRequest request) {
        if (Boolean.TRUE.equals(request.getIsGuest()) && (request.getFcMembers().size() > 0 || request.getFcResources().getMedia().length > 0))
            return false;
        return true;
    }
}
