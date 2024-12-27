package com.mockmate.auth_service.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mockmate.auth_service.dto.interview.InterviewTypeDTO;
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
    @AllArgsConstructor
    public static class RoomDetails {
        @JsonProperty("roomIDHash")
        private final String roomIDHash;

        @JsonProperty("interviewID")
        private final Long interviewID;

        @JsonProperty("interviewStartTime")
        private final OffsetTime interviewStartTime ;

        @JsonProperty("interviewEndTime")
        private final OffsetTime interviewEndTime ;

        @JsonProperty("interviewType")
        private final InterviewTypeDTO interviewTypeDto ;
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
