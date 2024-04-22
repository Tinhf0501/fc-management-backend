package com.luke.fcmanagement.module.football_club.controller;

import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.service.IFootballClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FootballClubController {
    private final IFootballClubService footballClubService;
//    @PostMapping("/test1")
//    public ResponseEntity<ApiResponse> test(@Valid @RequestBody CreateFCMemberRequest request) {
//        return ResponseEntity.ok(ApiResponse.ok(null));
//    }
//
//    @GetMapping("/test")
//    public ResponseEntity<ApiResponse> testGet(@RequestParam(name = "a") int a) {
//        return ResponseEntity.ok(ApiResponse.ok(null));
//    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateFCRequest request) {
        return ResponseEntity.ok(footballClubService.createFC(request));
    }
}
