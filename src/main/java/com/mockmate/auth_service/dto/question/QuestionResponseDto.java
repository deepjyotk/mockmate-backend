package com.mockmate.auth_service.dto.question;


import java.util.List;

public class QuestionResponseDto {
    private Long questionId;
    private String questionTextS3Url;
    private String questionTitle;
    private Long interviewTypeId;
    private Long questionDifficultyId;
    private List<String> tags;
    private List<CompanyFrequencyDto> companies;

    public QuestionResponseDto(Long questionId, String questionTextS3Url, String questionTitle, Long interviewTypeId, Long questionDifficultyId, List<String> tags, List<CompanyFrequencyDto> companies) {
        this.questionId = questionId;
        this.questionTextS3Url = questionTextS3Url;
        this.questionTitle = questionTitle;
        this.interviewTypeId = interviewTypeId;
        this.questionDifficultyId = questionDifficultyId;
        this.tags = tags;
        this.companies = companies;
    }

    public QuestionResponseDto() {
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public String getQuestionTextS3Url() {
        return this.questionTextS3Url;
    }

    public String getQuestionTitle() {
        return this.questionTitle;
    }

    public Long getInterviewTypeId() {
        return this.interviewTypeId;
    }

    public Long getQuestionDifficultyId() {
        return this.questionDifficultyId;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<CompanyFrequencyDto> getCompanies() {
        return this.companies;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setQuestionTextS3Url(String questionTextS3Url) {
        this.questionTextS3Url = questionTextS3Url;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setInterviewTypeId(Long interviewTypeId) {
        this.interviewTypeId = interviewTypeId;
    }

    public void setQuestionDifficultyId(Long questionDifficultyId) {
        this.questionDifficultyId = questionDifficultyId;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCompanies(List<CompanyFrequencyDto> companies) {
        this.companies = companies;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QuestionResponseDto)) return false;
        final QuestionResponseDto other = (QuestionResponseDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$questionId = this.getQuestionId();
        final Object other$questionId = other.getQuestionId();
        if (this$questionId == null ? other$questionId != null : !this$questionId.equals(other$questionId))
            return false;
        final Object this$questionText = this.getQuestionTextS3Url();
        final Object other$questionText = other.getQuestionTextS3Url();
        if (this$questionText == null ? other$questionText != null : !this$questionText.equals(other$questionText))
            return false;
        final Object this$questionTitle = this.getQuestionTitle();
        final Object other$questionTitle = other.getQuestionTitle();
        if (this$questionTitle == null ? other$questionTitle != null : !this$questionTitle.equals(other$questionTitle))
            return false;
        final Object this$interviewTypeId = this.getInterviewTypeId();
        final Object other$interviewTypeId = other.getInterviewTypeId();
        if (this$interviewTypeId == null ? other$interviewTypeId != null : !this$interviewTypeId.equals(other$interviewTypeId))
            return false;
        final Object this$questionDifficultyId = this.getQuestionDifficultyId();
        final Object other$questionDifficultyId = other.getQuestionDifficultyId();
        if (this$questionDifficultyId == null ? other$questionDifficultyId != null : !this$questionDifficultyId.equals(other$questionDifficultyId))
            return false;
        final Object this$tags = this.getTags();
        final Object other$tags = other.getTags();
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
        final Object this$companies = this.getCompanies();
        final Object other$companies = other.getCompanies();
        if (this$companies == null ? other$companies != null : !this$companies.equals(other$companies)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QuestionResponseDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $questionId = this.getQuestionId();
        result = result * PRIME + ($questionId == null ? 43 : $questionId.hashCode());
        final Object $questionText = this.getQuestionTextS3Url();
        result = result * PRIME + ($questionText == null ? 43 : $questionText.hashCode());
        final Object $questionTitle = this.getQuestionTitle();
        result = result * PRIME + ($questionTitle == null ? 43 : $questionTitle.hashCode());
        final Object $interviewTypeId = this.getInterviewTypeId();
        result = result * PRIME + ($interviewTypeId == null ? 43 : $interviewTypeId.hashCode());
        final Object $questionDifficultyId = this.getQuestionDifficultyId();
        result = result * PRIME + ($questionDifficultyId == null ? 43 : $questionDifficultyId.hashCode());
        final Object $tags = this.getTags();
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        final Object $companies = this.getCompanies();
        result = result * PRIME + ($companies == null ? 43 : $companies.hashCode());
        return result;
    }

    public String toString() {
        return "QuestionResponseDto(questionId=" + this.getQuestionId() + ", questionText=" + this.getQuestionTextS3Url() + ", questionTitle=" + this.getQuestionTitle() + ", interviewTypeId=" + this.getInterviewTypeId() + ", questionDifficultyId=" + this.getQuestionDifficultyId() + ", tags=" + this.getTags() + ", companies=" + this.getCompanies() + ")";
    }
}
