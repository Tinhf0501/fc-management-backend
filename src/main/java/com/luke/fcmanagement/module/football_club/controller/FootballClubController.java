package com.luke.fcmanagement.module.football_club.controller;

import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.dto.request.CreateFCMemberRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
//@AllArgsConstructor
//@NoArgsConstructor
@Slf4j
public class FootballClubController {
    @PostMapping("/test")
    private ResponseEntity<ApiResponse> test(@Valid @RequestBody CreateFCMemberRequest request) {
        log.info("vào api /test");
        return ResponseEntity.ok(new ApiResponse(null, null, "00", "OK", 123444L));
    }
}
