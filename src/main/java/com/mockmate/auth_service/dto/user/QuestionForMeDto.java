package com.mockmate.auth_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionForMeDto {
    private Long questionId;
    private String questionTitle;
}
