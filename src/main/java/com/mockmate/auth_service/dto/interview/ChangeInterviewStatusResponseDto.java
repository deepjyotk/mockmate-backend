package com.mockmate.auth_service.dto.interview;


public class ChangeInterviewStatusResponseDto {
    private Long upcomingInterviewId;
    private Long newStatusId;
    private String newStatusName;

    // Constructors
    public ChangeInterviewStatusResponseDto() {}

    public ChangeInterviewStatusResponseDto(Long upcomingInterviewId, Long newStatusId, String newStatusName) {
        this.upcomingInterviewId = upcomingInterviewId;
        this.newStatusId = newStatusId;
        this.newStatusName = newStatusName;
    }

    // Getters and Setters
    public Long getUpcomingInterviewId() {
        return upcomingInterviewId;
    }

    public void setUpcomingInterviewId(Long upcomingInterviewId) {
        this.upcomingInterviewId = upcomingInterviewId;
    }

    public Long getNewStatusId() {
        return newStatusId;
    }

    public void setNewStatusId(Long newStatusId) {
        this.newStatusId = newStatusId;
    }

    public String getNewStatusName() {
        return newStatusName;
    }

    public void setNewStatusName(String newStatusName) {
        this.newStatusName = newStatusName;
    }
}

