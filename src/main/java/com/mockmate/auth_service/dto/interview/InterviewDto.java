package com.mockmate.auth_service.dto.interview;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InterviewDto {

    @JsonProperty("interview_id")
    private Long interviewId;

    private String type;
    private String description;
    private Integer durationMinutes;
    private List<SlotDto> slots;

    // Constructors
    public InterviewDto() {
    }

    public InterviewDto(Long interviewId, String type, String description, Integer durationMinutes, List<SlotDto> slots) {
        this.interviewId = interviewId;
        this.type = type;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.slots = slots;
    }

    // Getters and Setters
    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public List<SlotDto> getSlots() {
        return slots;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setSlots(List<SlotDto> slots) {
        this.slots = slots;
    }
}
