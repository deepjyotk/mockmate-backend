package com.mockmate.auth_service.dto.user;

import java.time.OffsetDateTime;

public class UserSpecificUpcomingInterviewDto {
    private Long interviewId;
    private UserSpecificInterviewTypeDto interviewType;
    private OffsetDateTime interviewDateAndTime;
    private UserSpecificUpcomingInterviewQuestionDto upcomingInterviewQuestionForPeer;

    // Getters and Setters

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public UserSpecificInterviewTypeDto getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(UserSpecificInterviewTypeDto interviewType) {
        this.interviewType = interviewType;
    }

    public OffsetDateTime getInterviewDateAndTime() {
        return interviewDateAndTime;
    }

    public void setInterviewDateAndTime(OffsetDateTime interviewDateAndTime) {
        this.interviewDateAndTime = interviewDateAndTime;
    }

    public UserSpecificUpcomingInterviewQuestionDto getUpcomingInterviewQuestionForPeer() {
        return upcomingInterviewQuestionForPeer;
    }

    public void setUpcomingInterviewQuestionForPeer(UserSpecificUpcomingInterviewQuestionDto upcomingInterviewQuestionForPeer) {
        this.upcomingInterviewQuestionForPeer = upcomingInterviewQuestionForPeer;
    }
}