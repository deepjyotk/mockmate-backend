package com.mockmate.auth_service.entities.interview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(
        name = "upcoming_interviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"slot_id", "user_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "is_interviewer_role", nullable = true)
    private boolean isInterviewerRole;


    @Column(name = "room_id_hash", nullable = true)
    private String roomIDHash;


    @OneToOne(mappedBy = "upcomingInterview", cascade = CascadeType.ALL, orphanRemoval = true)
    private UpcomingInterviewUserPreference upcomingInterviewUserPreference;

}
