package com.mockmate.auth_service.dto.interview;


import jakarta.validation.constraints.NotNull;

public class ChangeInterviewStatusRequestDto {

    @NotNull(message = "Upcoming Interview ID cannot be null")
    private Long upcomingInterviewId;

    @NotNull(message = "Status ID cannot be null")
    private Long statusId;

    // Constructors
    public ChangeInterviewStatusRequestDto() {}

    public ChangeInterviewStatusRequestDto(Long upcomingInterviewId, Long statusId) {
        this.upcomingInterviewId = upcomingInterviewId;
        this.statusId = statusId;
    }

    // Getters and Setters
    public Long getUpcomingInterviewId() {
        return upcomingInterviewId;
    }

    public void setUpcomingInterviewId(Long upcomingInterviewId) {
        this.upcomingInterviewId = upcomingInterviewId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
