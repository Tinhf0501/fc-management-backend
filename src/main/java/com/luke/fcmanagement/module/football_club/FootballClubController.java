package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.annotation.SaveHistory;
import com.luke.fcmanagement.constants.ActionType;
import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fc")
public class FootballClubController {
    private final IFootballClubService footballClubService;

    @SaveHistory(value = ActionType.CREATE_FC)
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid CreateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException {
        return ResponseEntity.ok(footballClubService.createFC(request, bindingResult));
    }
}
