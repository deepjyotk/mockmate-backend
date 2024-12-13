package com.mockmate.auth_service.entities.interview;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "past_interviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date_and_time"}),
        indexes = @Index(name = "idx_user_id", columnList = "user_id")
)
public class PastInterviews {

    @Id
    @Column(name = "past_interview_id")
    private Long pastInterviewId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private InterviewStatus status;

    // Self-referential relationship for peerInterviewId
    @OneToOne
    @JoinColumn(name = "peer_interview_id", nullable = true)
    private PastInterviews peerInterview;

    // Field for questionIDFor_peer
    @Column(name = "question_id_for_peer", nullable = true)
    private Long questionIDForPeer;

    // New field for questionId
    @Column(name = "question_id", nullable = true)
    private Long questionId;


    @Column(name = "date_and_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dateAndTime;


    @ManyToOne
    @JoinColumn(name = "interview_type_id", nullable = false)
    private InterviewType interviewType;
}
