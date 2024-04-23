package com.luke.fcmanagement.module.football_club.service.impl;

import com.luke.fcmanagement.constants.*;
import com.luke.fcmanagement.entity.FCResourceEntity;
import com.luke.fcmanagement.entity.FootBallClubMemberEntity;
import com.luke.fcmanagement.entity.FootballClubEntity;
import com.luke.fcmanagement.model.ApiBody;
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
    private final FileSaver fileSaver;

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
        System.out.println(saveFileType);
        System.out.println(memberPath);
        ApiResponse response = new ApiResponse();
        if (!isValidFC(request, request.getFcResources().getMedia())) {
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

        // * save FC Member
        if (Objects.nonNull(request.getFcMembers())) {
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
        MultipartFile[] media = request.getFcResources().getMedia();
        if (Objects.nonNull(media) && media.length > 0) {
            if (!FileChecker.isValidListFile(media)) {
                throw new RuntimeException("List resource files invalid");
            } else {
                for (MultipartFile file : media) {
                    String fileName = fcSaved.getFcId() + "_" + file.getOriginalFilename();
                    String pathSave;
                    String type;
                    String desc;
                    if (FileChecker.isJPG(file)) {
                        pathSave = fileSaver.saveFile(file, fcImgPath, fileName);
                        type = FCMediaType.IMAGE.getValue();
                        desc = FCMediaType.IMAGE.getDisplay();
                    } else {
                        pathSave = fileSaver.saveFile(file, fcVideoPath, fileName);
                        type = FCMediaType.VIDEO.getValue();
                        desc = FCMediaType.VIDEO.getDisplay();
                    }

                    FCResourceEntity mediaFC = FCResourceEntity
                            .builder()
                            .path(pathSave)
                            .fcId(fcSaved.getFcId())
                            .type(type)
                            .description(desc)
                            .build();
                    fcResourceRepository.save(mediaFC);
                }
            }
        }
        ApiBody apiBody = new ApiBody();
        apiBody.put(FieldConstant.MESSAGE, Message.CREATE_FC_SUCCESS);
        return ApiResponse.ok(apiBody);
    }

    private boolean isValidFC(CreateFCRequest request, MultipartFile[] media) {
        if (Boolean.TRUE.equals(request.getIsGuest()) && (request.getFcMembers().size() > 0 || media.length > 0))
            return false;
        return true;
    }
}
