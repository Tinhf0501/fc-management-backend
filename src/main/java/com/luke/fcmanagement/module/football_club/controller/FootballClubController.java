package com.luke.fcmanagement.module.football_club.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.service.IFootballClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fc")
public class FootballClubController {

    private final IFootballClubService footballClubService;

    @PostMapping
    public ApiResponse create(@Valid CreateFCRequest request) throws JsonProcessingException {
        return footballClubService.createFC(request);
    }
}
