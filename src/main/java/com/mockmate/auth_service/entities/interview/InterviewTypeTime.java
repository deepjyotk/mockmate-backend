package com.mockmate.auth_service.entities.interview;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "interview_type_times", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"interview_type_id", "time"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
