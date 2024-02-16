package com.luke.fcmanagement.module.football_club.controller;

import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCMemberRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@AllArgsConstructor
//@NoArgsConstructor
@Slf4j
public class FootballClubController {
    @PostMapping("/test1")
    public ResponseEntity<ApiResponse> test(@Valid @RequestBody CreateFCMemberRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> testGet(@RequestParam(name = "a") int a) {
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

}
