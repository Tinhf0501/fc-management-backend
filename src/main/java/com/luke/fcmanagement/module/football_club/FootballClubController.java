package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.request.SearchFcRequest;
import com.luke.fcmanagement.module.football_club.request.UpdateFCRequest;
import com.luke.fcmanagement.module.history.annotation.CaptureHistory;
import com.luke.fcmanagement.module.history.constant.ActionType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fc")
public class FootballClubController {
    private final IFootballClubService footballClubService;

    @CaptureHistory(ActionType.SEARCH_FC)
    @PostMapping("/search")
    public ApiResponse search(@Valid @RequestBody SearchFcRequest request) throws BusinessException {
        return footballClubService.searchFC(request);
    }

    @CaptureHistory(ActionType.CREATE_FC)
    @PostMapping
    public ApiResponse create(@Valid CreateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException {
        return footballClubService.createFC(request, bindingResult);
    }

    @CaptureHistory(ActionType.UPDATE_FC)
    @PutMapping
    public ApiResponse update(@Valid UpdateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException {
        return footballClubService.updateFC(request, bindingResult);
    }
}
