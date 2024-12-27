package com.mockmate.auth_service.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserSpecificResponseDto {

    private UserInfo userInfo ;
    private List<UserSpecificUpcomingInterviewDto> upcomingInterviews;
    private PastInterviewsPageDto pastInterviews;

    @Data
    @AllArgsConstructor
    public static class UserInfo{
        String username;
        String userProfileUrl ;
    }
}
