package com.mockmate.auth_service.entities.interview;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(
        name = "past_interviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date_and_time"}),
        indexes = @Index(name = "idx_user_id", columnList = "user_id")
)
public class PastInterviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "past_interview_id")
    private Long pastInterviewId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private InterviewStatus status;

    // Self-referential relationship for peerInterviewId
    @ManyToOne
    @JoinColumn(name = "peer_interview_id", nullable = true)
    private PastInterviews peerInterview;

    // Field for questionIDFor_peer
    @Column(name = "question_id_for_peer", nullable = true)
    private Long questionIDForPeer;

    // New field for questionId
    @Column(name = "question_id", nullable = true)
    private Long questionId;

    // New field for date and time in UTC format (ISO 8601)
    @Column(name = "date_and_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dateAndTime;

    // New field for interviewTypeId
    @ManyToOne
    @JoinColumn(name = "interview_type_id", nullable = false)
    private InterviewType interviewType;

    // Getters and Setters
    public Long getPastInterviewId() {
        return pastInterviewId;
    }

    public void setPastInterviewId(Long pastInterviewId) {
        this.pastInterviewId = pastInterviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public PastInterviews getPeerInterview() {
        return peerInterview;
    }

    public void setPeerInterview(PastInterviews peerInterview) {
        this.peerInterview = peerInterview;
    }

    public Long getQuestionIDForPeer() {
        return questionIDForPeer;
    }

    public void setQuestionIDForPeer(Long questionIDForPeer) {
        this.questionIDForPeer = questionIDForPeer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public OffsetDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(OffsetDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    // Ensure UTC format before persisting
    @PrePersist
    @PreUpdate
    public void ensureUTCDateAndTime() {
        if (dateAndTime != null && dateAndTime.getOffset() != OffsetDateTime.now().getOffset()) {
            // Adjust to UTC if it's not in UTC format
            this.dateAndTime = this.dateAndTime.withOffsetSameInstant(OffsetDateTime.now().getOffset());
        }
    }
}
