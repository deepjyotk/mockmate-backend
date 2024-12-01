package com.mockmate.auth_service.dto.user;


import java.util.List;

public class UserSpecificResponseDto {
    private List<UserSpecificUpcomingInterviewDto> upcomingInterviews;
    private PastInterviewsPageDto pastInterviews;

    // Getters and Setters

    public List<UserSpecificUpcomingInterviewDto> getUpcomingInterviews() {
        return upcomingInterviews;
    }

    public void setUpcomingInterviews(List<UserSpecificUpcomingInterviewDto> upcomingInterviews) {
        this.upcomingInterviews = upcomingInterviews;
    }

    public PastInterviewsPageDto getPastInterviews() {
        return pastInterviews;
    }

    public void setPastInterviews(PastInterviewsPageDto pastInterviews) {
        this.pastInterviews = pastInterviews;
    }
}
