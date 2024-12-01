package com.mockmate.auth_service.dto.interview;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class InitResponseDto {

    @JsonProperty("interview_levels")
    private List<InterviewLevelDto> interviewLevels;

    @JsonProperty("interviews")
    private List<InterviewDto> interviews;

    // Constructors
    public InitResponseDto() {
    }

    public InitResponseDto(List<InterviewLevelDto> interviewLevels, List<InterviewDto> interviews) {
        this.interviewLevels = interviewLevels;
        this.interviews = interviews;
    }

    // Getters and Setters
    public List<InterviewLevelDto> getInterviewLevels() {
        return interviewLevels;
    }

    public void setInterviewLevels(List<InterviewLevelDto> interviewLevels) {
        this.interviewLevels = interviewLevels;
    }

    public List<InterviewDto> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<InterviewDto> interviews) {
        this.interviews = interviews;
    }
}
