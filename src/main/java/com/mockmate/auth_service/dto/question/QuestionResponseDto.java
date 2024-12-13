package com.mockmate.auth_service.dto.question;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {

    @JsonProperty("questionID")
    private Long questionId;

    @JsonProperty("questionS3Url")
    private String questionTextS3Url;

    @JsonProperty("questionTitle")
    private String questionTitle;

    @JsonProperty("interviewTypeId")
    private Long interviewTypeId;

    @JsonProperty("questionDifficultyID")
    private Long questionDifficultyId;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("companies")
    private List<CompanyFrequencyDto> companies;
}
