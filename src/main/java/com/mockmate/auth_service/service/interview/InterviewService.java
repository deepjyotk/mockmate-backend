package com.mockmate.auth_service.service.interview;

import com.mockmate.auth_service.dto.interview.*;

import java.util.Optional;

public interface InterviewService {

    InterviewScheduleResponseDto scheduleInterview(InterviewScheduleDto interviewScheduleDto);

    Optional<UpcomingInterviewsDto> updateInterview(String id, UpcomingInterviewsDto updatedFields);

    boolean deleteInterview(String id);


    /**
     * Join the waiting room by updating the interview status.
     *
     * @param interviewId The ID of the interview to update.
     */
    void joinWaitingRoom(Long interviewId);


    ChangeInterviewStatusResponseDto changeInterviewStatus(ChangeInterviewStatusRequestDto requestDto);


}
