package com.mockmate.auth_service.dto.interview;


import com.fasterxml.jackson.annotation.JsonProperty;

public class InterviewScheduleDto {

    @JsonProperty("interviewTypeId")
    private String interviewTypeId;

    @JsonProperty("interviewLevelId")
    private String interviewLevelId;

    @JsonProperty("interviewSlotId")
    private String interviewSlotId;

    @JsonProperty("additionalDescription")
    private String additionalDescription;

    // Getters and Setters
    public String getInterviewTypeId() {
        return interviewTypeId;
    }

    public void setInterviewTypeId(String interviewTypeId) {
        this.interviewTypeId = interviewTypeId;
    }

    public String getInterviewLevelId() {
        return interviewLevelId;
    }

    public void setInterviewLevelId(String interviewLevelId) {
        this.interviewLevelId = interviewLevelId;
    }

    public String getInterviewSlotId() {
        return interviewSlotId;
    }

    public void setInterviewSlotId(String interviewSlotId) {
        this.interviewSlotId = interviewSlotId;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }
}
