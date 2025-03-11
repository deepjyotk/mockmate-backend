package com.mockmate.auth_service.service.user_service;

import com.mockmate.auth_service.dto.interview.InitResponseDto;
import com.mockmate.auth_service.dto.login.LoginRequestDto;
import com.mockmate.auth_service.dto.login.LoginResponseDto;
import com.mockmate.auth_service.dto.register.RegisterRequestDto;
import com.mockmate.auth_service.dto.register.RegisterResponseDto;
import com.mockmate.auth_service.dto.user.UserSpecificResponseDto;

public interface IUserService {
    String getUsername(Long userID);
    boolean usernameOrEmailExists(String userNameOrEmail);
    String generateJwtToken(String userNameOrEmail);
    RegisterResponseDto register(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
    InitResponseDto getInitData()  ;
    UserSpecificResponseDto getUserSpecificData(Long userId, int currentPage, int limit);
    void cleanUpcomingInterviews(Long userId) ;
}
