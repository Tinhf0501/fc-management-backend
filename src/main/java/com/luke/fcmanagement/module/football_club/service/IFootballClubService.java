package com.luke.fcmanagement.module.football_club.service;

import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IFootballClubService {
    ApiResponse createFC(CreateFCRequest request);
}
