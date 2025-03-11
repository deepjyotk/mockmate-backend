package com.mockmate.auth_service.dto.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QTagDto {

    @JsonProperty("tagID")
    private Long tagID;
    private String questionTagText;
}
