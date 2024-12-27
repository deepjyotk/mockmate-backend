package com.mockmate.auth_service.entities.interview;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interview_feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "past_interview_id", nullable = false, unique = true)
    private PastInterviews pastInterview;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "communication_skills_rating", nullable = false)
    private Integer communicationSkillsRating;

    @Min(1)
    @Max(5)
    @Column(name = "technical_skills_rating", nullable = true)
    private Integer technicalSkillsRating;

    @Column(name = "did_well_text", length = 500) // adjust length as needed
    private String didWellText;

    @Column(name = "things_to_improve_text", length = 500)
    private String thingsToImproveText;

    @Column(name = "next_round_selection", nullable = false)
    private Boolean nextRoundSelection;

    @Min(1)
    @Max(5)
    @Column(name = "good_match_for_peer_rating", nullable = false)
    private Integer goodMatchForPeerRating;
}
