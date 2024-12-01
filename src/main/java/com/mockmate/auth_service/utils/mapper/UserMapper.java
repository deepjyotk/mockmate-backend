package com.mockmate.auth_service.utils.mapper;

import com.mockmate.auth_service.dto.register.RegisterRequestDto;
import com.mockmate.auth_service.dto.register.RegisterResponseDto;
import com.mockmate.auth_service.entities.UserEntity;

import java.time.LocalDateTime;

public class UserMapper {

    // Convert RegisterRequest to UserEntity
    public static UserEntity toEntity(RegisterRequestDto request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setUniversityMajor(request.getUniversityMajor());
        user.setUniversityName(request.getUniversityName());
        user.setRolesTargeting(request.getRolesTargeting());
        user.setRelevantWorkExperience(request.getRelevantWorkExperience());

        // Setting default timestamps
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return user;
    }

    // Convert UserEntity to RegisterResponse
    public static RegisterResponseDto toRegisterResponse(UserEntity user, String token) {
        return new RegisterResponseDto(
                token,
                "User registered successfully",
                user.getUsername(),
                user.getEmail()
        );
    }
}
