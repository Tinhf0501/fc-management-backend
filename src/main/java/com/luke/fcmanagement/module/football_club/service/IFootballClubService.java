package com.luke.fcmanagement.module.football_club.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;

public interface IFootballClubService {
    ApiResponse createFC(CreateFCRequest request) throws JsonProcessingException;
}
