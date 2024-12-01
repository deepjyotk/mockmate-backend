package com.mockmate.auth_service.entities.interview;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "interview_slot_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"interview_type_time_id", "interview_date"})
})
public class InterviewSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Long slotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_type_time_id", nullable = false)
    private InterviewTypeTime interviewTypeTime;

    @Column(name = "interview_date", nullable = false)
    private LocalDate interviewDate;

    // Constructors
    public InterviewSlot() {
    }

    public InterviewSlot(InterviewTypeTime interviewTypeTime, LocalDate interviewDate) {
        this.interviewTypeTime = interviewTypeTime;
        this.interviewDate = interviewDate;
    }

    // Getters and Setters
    public Long getSlotId() {
        return slotId;
    }

    // No setter for slotId since it's auto-generated
    public InterviewTypeTime getInterviewTypeTime() {
        return interviewTypeTime;
    }
    public void setInterviewTypeTime(InterviewTypeTime interviewTypeTime) {
        this.interviewTypeTime = interviewTypeTime;
    }
    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }
}
