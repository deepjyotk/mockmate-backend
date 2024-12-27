package com.mockmate.auth_service.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCompanyDto {
    private Long id;
    private Long companyId;
    private Integer frequencyAsked;
    private LocalDateTime lastAskedDate;
}