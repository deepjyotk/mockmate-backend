package com.mockmate.auth_service.dto.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionPopulateRequestDto {


    @JsonProperty("interviewTypeId")
    private Long interviewTypeId;

    @JsonProperty("questionDifficultyId")
    private Long questionDifficultyId;

    @JsonProperty("questionTitle")
    private String questionTitle;

    @JsonProperty("tags")
    private List<QTagDto> tags;

    @JsonProperty("companies")
    private List<CompanyFrequencyDto> companies;

    @JsonProperty("markdownFile")
    private MultipartFile markdownFile;
}