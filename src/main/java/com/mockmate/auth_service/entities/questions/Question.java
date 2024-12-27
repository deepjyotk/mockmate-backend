package com.mockmate.auth_service.entities.questions;

import com.mockmate.auth_service.entities.interview.InterviewLevel;
import com.mockmate.auth_service.entities.interview.InterviewType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions_db",
        indexes = {
                @Index(name = "idx_interview_type", columnList = "interviewType_id"),
                @Index(name = "idx_question_difficulty", columnList = "interviewLevel_id")
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

}