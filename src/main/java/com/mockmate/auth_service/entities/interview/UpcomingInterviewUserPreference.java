package com.mockmate.auth_service.entities.interview;

import jakarta.persistence.*;

@Entity
@Table(name = "upcoming_interviews_user_preference")
public class UpcomingInterviewUserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id", nullable = false)
    private Long preferenceId;

    @OneToOne
    @JoinColumn(name = "upcoming_interview_id", nullable = false)
    private UpcomingInterviews upcomingInterview;

    @Column(name = "preference_interview_level_id", nullable = false)
    private Long preferenceInterviewLevelId;

    @Column(name = "preference_description")
    private String preferenceDescription;

    // Getters and Setters
    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public UpcomingInterviews getUpcomingInterview() {
        return upcomingInterview;
    }

    public void setUpcomingInterview(UpcomingInterviews upcomingInterview) {
        this.upcomingInterview = upcomingInterview;
    }

    public Long getPreferenceInterviewLevelId() {
        return preferenceInterviewLevelId;
    }

    public void setPreferenceInterviewLevelId(Long preferenceInterviewLevelId) {
        this.preferenceInterviewLevelId = preferenceInterviewLevelId;
    }

    public String getPreferenceDescription() {
        return preferenceDescription;
    }

    public void setPreferenceDescription(String preferenceDescription) {
        this.preferenceDescription = preferenceDescription;
    }
}
