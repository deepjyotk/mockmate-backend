package com.mockmate.auth_service.dto.room;

import com.mockmate.auth_service.dto.question.QuestionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomPayloadDTO {
    private RoomDetails roomDetails;
    private UserDetails userDetails;
    private PeerInfo peerInfo;

    @Data
    public static class RoomDetails {
        private String roomIDHash;
        private Long interviewID;
        private OffsetTime interviewStartTime ;
        private OffsetTime interviewEndTime ;
    }

    @Data
    public static class UserDetails {
        private Long userID;
        private String userName;
        private String fullName;
        private String interviewRole; // "Interviewer" or "Interviewee"
    }


    @Data
    public static class PeerInfo {
        private String peerRole; // "Interviewer" or "Interviewee"
        private QuestionResponseDto question;
    }
}
