package com.mockmate.auth_service.dto.user;

import java.time.OffsetDateTime;

public class PastInterviewDto {
    private Long pastInterviewID;
    private OffsetDateTime pastInterviewDateAndTime;
    private String pastInterviewType;
    private QuestionForMeDto questionForMe;
    private PeerUserDto peerUser;

    // Getters and Setters

    public Long getPastInterviewID() {
        return pastInterviewID;
    }

    public void setPastInterviewID(Long pastInterviewID) {
        this.pastInterviewID = pastInterviewID;
    }

    public OffsetDateTime getPastInterviewDateAndTime() {
        return pastInterviewDateAndTime;
    }

    public void setPastInterviewDateAndTime(OffsetDateTime pastInterviewDateAndTime) {
        this.pastInterviewDateAndTime = pastInterviewDateAndTime;
    }

    public String getPastInterviewType() {
        return pastInterviewType;
    }

    public void setPastInterviewType(String pastInterviewType) {
        this.pastInterviewType = pastInterviewType;
    }

    public QuestionForMeDto getQuestionForMe() {
        return questionForMe;
    }

    public void setQuestionForMe(QuestionForMeDto questionForMe) {
        this.questionForMe = questionForMe;
    }

    public PeerUserDto getPeerUser() {
        return peerUser;
    }

    public void setPeerUser(PeerUserDto peerUser) {
        this.peerUser = peerUser;
    }
}
