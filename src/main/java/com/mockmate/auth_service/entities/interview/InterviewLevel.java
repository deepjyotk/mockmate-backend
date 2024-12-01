package com.mockmate.auth_service.entities.interview;


import jakarta.persistence.*;


@Entity
@Table(name = "interview_levels")
public class InterviewLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String level;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Constructors
    public InterviewLevel() {
    }

    public InterviewLevel(Long id, String level, String description) {
        this.id = id;
        this.level = level;
        this.description = description;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
