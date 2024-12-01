package com.mockmate.auth_service.entities.interview;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "interview_types")
public class InterviewType {

    @Id
    private Long id;

    @Column(length = 100)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @OneToMany(mappedBy = "interviewType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InterviewTypeTime> times;

    // Constructors
    public InterviewType() {
    }

    public InterviewType(Long id, String type, String description, Integer durationMinutes) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Set<InterviewTypeTime> getTimes() {
        return times;
    }

    public void setTimes(Set<InterviewTypeTime> times) {
        this.times = times;
    }
}
