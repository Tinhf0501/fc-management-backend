package com.luke.fcmanagement.module.football_club.controller;

import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCMemberRequest;
import com.luke.fcmanagement.utils.ResponseUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
//@AllArgsConstructor
//@NoArgsConstructor
public class FootballClubController {
    @PostMapping("/test")
    private ResponseEntity<ApiResponse> test(@Valid @RequestBody CreateFCMemberRequest request) {
        return ResponseEntity.ok(new ApiResponse());
    }
}
