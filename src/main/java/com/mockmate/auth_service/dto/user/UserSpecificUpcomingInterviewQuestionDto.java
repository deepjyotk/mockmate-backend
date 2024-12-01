package com.mockmate.auth_service.dto.user;

public class UserSpecificUpcomingInterviewQuestionDto {
    private Long questionID;
    private String questionTitle;
    private String questionDescription;

    public UserSpecificUpcomingInterviewQuestionDto(Long questionID, String questionTitle, String questionDescription) {
        this.questionID = questionID;
        this.questionTitle = questionTitle;
        this.questionDescription = questionDescription;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }
}
