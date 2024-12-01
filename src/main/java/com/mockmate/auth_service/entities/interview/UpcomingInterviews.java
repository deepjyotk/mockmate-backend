package com.mockmate.auth_service.entities.interview;
import jakarta.persistence.*;
@Entity
@Table(
        name = "upcoming_interviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"slot_id", "user_id"})
)
public class UpcomingInterviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "upcoming_interview_id")
    private Long upcomingInterviewId;

    @Column(name = "slot_id", nullable = false)
    private Long slotId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private InterviewStatus status;

    // Self-referential relationship for peerInterviewId
    @ManyToOne
    @JoinColumn(name = "peer_interview_id", nullable = true)
    private UpcomingInterviews peerInterview;

    // Field for questionIDFor_peer
    @Column(name = "question_id_for_peer", nullable = true)
    private Long questionIDForPeer;

    // New field for questionId
    @Column(name = "question_id", nullable = true)
    private Long questionId;

    // Getters and Setters
    public Long getUpcomingInterviewId() {
        return upcomingInterviewId;
    }

    public void setUpcomingInterviewId(Long upcomingInterviewId) {
        this.upcomingInterviewId = upcomingInterviewId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
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

    public UpcomingInterviews getPeerInterview() {
        return peerInterview;
    }

    public void setPeerInterview(UpcomingInterviews peerInterview) {
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
}
