package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public interface IFootballClubService {
    ApiResponse createFC(CreateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException;
}
