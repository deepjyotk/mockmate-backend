package com.mockmate.auth_service.entities.interview;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "interview_type_times", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"interview_type_id", "time"})
})
public class InterviewTypeTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "interview_type_id", nullable = false)
    private InterviewType interviewType;

    @Column(name = "time", nullable = false)
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\dZ$", message = "Time must be in ISO format with 'Z' suffix")
    private String time;

    // Constructors
    public InterviewTypeTime() {
    }

    public InterviewTypeTime(InterviewType interviewType, String time) {
        this.interviewType = interviewType;
        this.time = time;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No setter for id since it's auto-generated

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
