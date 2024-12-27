package com.mockmate.auth_service.dto.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CompanyFrequencyDto {
    @JsonProperty("companyID")
    private Long companyId;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("frequencyAsked")
    private Integer frequencyAsked;

    @JsonProperty("lastAskedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastAskedDate; // Can be null; handle default date in service layer if needed
}