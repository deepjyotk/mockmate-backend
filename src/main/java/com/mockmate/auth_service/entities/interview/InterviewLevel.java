package com.mockmate.auth_service.entities.interview;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "interview_levels")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterviewLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String level;

    @Column(columnDefinition = "TEXT")
    private String description;
}
