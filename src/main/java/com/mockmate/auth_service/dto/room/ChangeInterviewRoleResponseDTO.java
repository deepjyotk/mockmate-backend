package com.mockmate.auth_service.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInterviewRoleResponseDTO {

    private String roomHash;

    private PeerDTO peer1;

    private PeerDTO peer2;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeerDTO {
        private Long interviewId;
        private String interviewRole; // Possible values: "Interviewer" or "Interviewee"
    }
}
