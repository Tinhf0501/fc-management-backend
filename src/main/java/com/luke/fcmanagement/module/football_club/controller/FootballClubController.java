package com.luke.fcmanagement.module.football_club.controller;

import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.service.IFootballClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fc")
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
    public ResponseEntity<ApiResponse> create(
            @Valid CreateFCRequest request
    ) {
        return ResponseEntity.ok(footballClubService.createFC(request));
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> test(
    ) {
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
