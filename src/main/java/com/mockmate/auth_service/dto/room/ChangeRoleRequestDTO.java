package com.mockmate.auth_service.dto.room;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "Request DTO for changing interview role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeRoleRequestDTO {

    @Schema(description = "Hash of the room", example = "abc123xyz")
    private String roomHash;

    @Schema(description = "ID of the interview", example = "1")
    private Long interviewID;
}