package com.mockmate.auth_service.entities.interview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "interview_types")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
