package com.mockmate.auth_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PastInterviewDTO {
    private Long pastInterviewID;
    private OffsetDateTime pastInterviewDateAndTime;
    private String pastInterviewType;
    private QuestionForMeDto questionForMe;
    private PeerUserDto peerUser;
    private String roomIDHash;
    private Boolean feedbackGiven;
    private FeedbackByPeerDTO feedbackByPeer ;
}
