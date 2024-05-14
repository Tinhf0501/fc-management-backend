package com.luke.fcmanagement.module.football_club;

import com.luke.fcmanagement.exception.BusinessException;
import com.luke.fcmanagement.model.ApiResponse;
import com.luke.fcmanagement.model.SearchRequest;
import com.luke.fcmanagement.module.football_club.request.CreateFCRequest;
import com.luke.fcmanagement.module.football_club.request.SearchFcRequest;
import com.luke.fcmanagement.module.football_club.request.UpdateFCRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public interface IFootballClubService {
    ApiResponse createFC(CreateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException;

    ApiResponse updateFC(UpdateFCRequest request, BindingResult bindingResult) throws BusinessException, BindException;

    ApiResponse searchFC(SearchRequest<SearchFcRequest> request) throws BusinessException;

    ApiResponse detail(Long fcId);
}
