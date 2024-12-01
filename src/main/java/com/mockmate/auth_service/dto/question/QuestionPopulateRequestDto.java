package com.mockmate.auth_service.dto.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mockmate.auth_service.entities.questions.QTag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class QuestionPopulateRequestDto {


    @JsonProperty("interviewTypeId")
    private Long interviewTypeId;

    @JsonProperty("questionDifficultyId")
    private Long questionDifficultyId;

    @JsonProperty("questionTitle")
    private String questionTitle;

    @JsonProperty("tags")
    private List<QTag> tags;

    @JsonProperty("companies")
    private List<CompanyFrequencyDto> companies; // List of companies with frequency and last asked date

    @JsonProperty("markdownFile")
    private MultipartFile markdownFile;

    public QuestionPopulateRequestDto(Long interviewTypeId, Long questionDifficultyId, String questionTitle, List<QTag> tags, List<CompanyFrequencyDto> companies, MultipartFile markdownFile) {
        this.interviewTypeId = interviewTypeId;
        this.questionDifficultyId = questionDifficultyId;
        this.questionTitle = questionTitle;
        this.tags = tags;
        this.companies = companies;
        this.markdownFile = markdownFile ;
    }

    public QuestionPopulateRequestDto() {
    }


    public Long getInterviewTypeId() {
        return this.interviewTypeId;
    }

    public Long getQuestionDifficultyId() {
        return this.questionDifficultyId;
    }

    public String getQuestionTitle() {
        return this.questionTitle;
    }

    public List<QTag> getTags() {
        return this.tags;
    }

    public List<CompanyFrequencyDto> getCompanies() {
        return this.companies;
    }

    public void setInterviewTypeId(Long interviewTypeId) {
        this.interviewTypeId = interviewTypeId;
    }

    public void setQuestionDifficultyId(Long questionDifficultyId) {
        this.questionDifficultyId = questionDifficultyId;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setTags(List<QTag> tags) {
        this.tags = tags;
    }

    public void setCompanies(List<CompanyFrequencyDto> companies) {
        this.companies = companies;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QuestionPopulateRequestDto)) return false;
        final QuestionPopulateRequestDto other = (QuestionPopulateRequestDto) o;
        if (!other.canEqual((Object) this)) return false;

        final Object this$interviewTypeId = this.getInterviewTypeId();
        final Object other$interviewTypeId = other.getInterviewTypeId();
        if (this$interviewTypeId == null ? other$interviewTypeId != null : !this$interviewTypeId.equals(other$interviewTypeId))
            return false;
        final Object this$questionDifficultyId = this.getQuestionDifficultyId();
        final Object other$questionDifficultyId = other.getQuestionDifficultyId();
        if (this$questionDifficultyId == null ? other$questionDifficultyId != null : !this$questionDifficultyId.equals(other$questionDifficultyId))
            return false;
        final Object this$questionTitle = this.getQuestionTitle();
        final Object other$questionTitle = other.getQuestionTitle();
        if (this$questionTitle == null ? other$questionTitle != null : !this$questionTitle.equals(other$questionTitle))
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
        return other instanceof QuestionPopulateRequestDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $interviewTypeId = this.getInterviewTypeId();
        result = result * PRIME + ($interviewTypeId == null ? 43 : $interviewTypeId.hashCode());
        final Object $questionDifficultyId = this.getQuestionDifficultyId();
        result = result * PRIME + ($questionDifficultyId == null ? 43 : $questionDifficultyId.hashCode());
        final Object $questionTitle = this.getQuestionTitle();
        result = result * PRIME + ($questionTitle == null ? 43 : $questionTitle.hashCode());
        final Object $tags = this.getTags();
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        final Object $companies = this.getCompanies();
        result = result * PRIME + ($companies == null ? 43 : $companies.hashCode());
        return result;
    }

    public String toString() {
        return "QuestionPopulateRequestDto(interviewTypeId=" + this.getInterviewTypeId() + ", questionDifficultyId=" + this.getQuestionDifficultyId() + ", questionTitle=" + this.getQuestionTitle() + ", tags=" + this.getTags() + ", companies=" + this.getCompanies() + ")";
    }

    public MultipartFile getMarkdownFile() {
        return this.markdownFile;
    }

    @JsonProperty("markdownFile")
    public void setMarkdownFile(MultipartFile markdownFile) {
        this.markdownFile = markdownFile;
    }
}