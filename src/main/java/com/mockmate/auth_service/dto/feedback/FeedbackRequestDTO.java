package com.mockmate.auth_service.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestDTO {
    private String roomHash ;

    private PeerFeedbackDTO peerFeedback;

    // Getters and Setters

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeerFeedbackDTO {
        private Integer communicationSkillsRating; // 1-5
        private Integer technicalSkillsRating; // 1-5 (nullable)
        private String didWellText;
        private String thingsToImproveText;
        private Boolean nextRoundSelection;
        private Integer goodMatchForPeerRating; // 1-5

        // Getters and Setters
    }

    // Getters and Setters for interviewID, peerInterviewID, peerFeedback
}
