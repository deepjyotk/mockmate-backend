package com.mockmate.auth_service.dto.interview;

import com.mockmate.auth_service.validation.annotation.ValidInterviewLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterviewLevelDto {
    private Long id;

    @ValidInterviewLevel
    private String level;

    private String description;

}
