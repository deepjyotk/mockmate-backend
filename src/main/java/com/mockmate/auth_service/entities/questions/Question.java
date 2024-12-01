package com.mockmate.auth_service.entities.questions;

import com.mockmate.auth_service.entities.interview.InterviewLevel;
import com.mockmate.auth_service.entities.interview.InterviewType;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "questions_db",
        indexes = {
                @Index(name = "idx_interview_type", columnList = "interviewType_id"),
                @Index(name = "idx_question_difficulty", columnList = "interviewLevel_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_question_code", columnNames = "questionCode")
        }
)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 2048)
    private String questionTextS3Url; // Assuming this holds the S3 URL as TEXT

    @ManyToOne
    @JoinColumn(name = "interviewType_id", nullable = false)
    @ToString.Exclude
    private InterviewType interviewType;

    @ManyToOne
    @JoinColumn(name = "interviewLevel_id", nullable = false)
    @ToString.Exclude
    private InterviewLevel interviewLevel;

    @Column(nullable = false, unique = true)
    private String questionTitle;


    @Column(length = 2048)
    private String description; // Additional description for the question

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<QuestionTagCombo> tagCombos;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<QuestionCompany> companyAssociations;

    public Question(Long questionId, String questionTextS3Url, InterviewType interviewType,
                    InterviewLevel interviewLevel, String questionTitle, String questionCode,
                    String description, Set<QuestionTagCombo> tagCombos, Set<QuestionCompany> companyAssociations) {
        this.questionId = questionId;
        this.questionTextS3Url = questionTextS3Url;
        this.interviewType = interviewType;
        this.interviewLevel = interviewLevel;
        this.questionTitle = questionTitle;
        this.description = description;
        this.tagCombos = tagCombos;
        this.companyAssociations = companyAssociations;
    }

    public Question() {
    }

    // Getters and Setters

    public Long getQuestionId() {
        return this.questionId;
    }

    public String getQuestionTextS3Url() {
        return this.questionTextS3Url;
    }

    public InterviewType getInterviewType() {
        return this.interviewType;
    }

    public InterviewLevel getInterviewLevel() {
        return this.interviewLevel;
    }

    public String getQuestionTitle() {
        return this.questionTitle;
    }



    public String getDescription() {
        return this.description;
    }

    public Set<QuestionTagCombo> getTagCombos() {
        return this.tagCombos;
    }

    public Set<QuestionCompany> getCompanyAssociations() {
        return this.companyAssociations;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setQuestionTextS3Url(String questionTextS3Url) {
        this.questionTextS3Url = questionTextS3Url;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public void setInterviewLevel(InterviewLevel interviewLevel) {
        this.interviewLevel = interviewLevel;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setTagCombos(Set<QuestionTagCombo> tagCombos) {
        this.tagCombos = tagCombos;
    }

    public void setCompanyAssociations(Set<QuestionCompany> companyAssociations) {
        this.companyAssociations = companyAssociations;
    }
}