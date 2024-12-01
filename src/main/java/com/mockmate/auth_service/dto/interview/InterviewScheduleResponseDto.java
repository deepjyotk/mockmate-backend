package com.mockmate.auth_service.dto.interview;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class InterviewScheduleResponseDto {

    @JsonProperty("upcoming_interview_id")
    private Long upcomingInterviewId;

    // Constructor
    public InterviewScheduleResponseDto(Long upcomingInterviewId) {
        this.upcomingInterviewId = upcomingInterviewId;
    }

    // Getter and Setter
    public Long getUpcomingInterviewId() {
        return upcomingInterviewId;
    }

    public void setUpcomingInterviewId(Long upcomingInterviewId) {
        this.upcomingInterviewId = upcomingInterviewId;
    }
}
