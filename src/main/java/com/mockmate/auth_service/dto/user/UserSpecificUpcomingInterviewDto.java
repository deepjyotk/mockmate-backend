package com.mockmate.auth_service.dto.user;

import com.mockmate.auth_service.dto.interview.InterviewTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSpecificUpcomingInterviewDto {
    private Long interviewId;
    private InterviewTypeDTO interviewType;
    private OffsetDateTime interviewDateAndTime;
    private UserSpecificUpcomingInterviewQuestionDto upcomingInterviewQuestionForPeer;

}