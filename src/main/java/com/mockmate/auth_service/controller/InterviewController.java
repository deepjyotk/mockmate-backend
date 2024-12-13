package com.mockmate.auth_service.controller;

import com.mockmate.auth_service.dto.ResponseDto;
import com.mockmate.auth_service.dto.feedback.FeedbackRequestDTO;
import com.mockmate.auth_service.dto.interview.*;
import com.mockmate.auth_service.service.interview.InterviewService;
import com.mockmate.auth_service.service.interview_feedback.InterviewFeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;


    @Autowired
    private InterviewFeedbackService interviewFeedbackService;


    // POST: Create an interview
    @PostMapping("/scheduleInterview")
    public ResponseEntity<ResponseDto<InterviewScheduleResponseDto> > scheduleInterview(@RequestBody InterviewScheduleDto interview) {
        InterviewScheduleResponseDto createdInterview = interviewService.scheduleInterview(interview);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(createdInterview,HttpStatus.CREATED));
    }

    // PATCH: Update interview type and time
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateInterview(@PathVariable String id, @RequestBody UpcomingInterviewsDto updatedFields) {
        Optional<UpcomingInterviewsDto> updatedInterview = interviewService.updateInterview(id, updatedFields);
        if (updatedInterview.isPresent()) {
            return ResponseEntity.ok(updatedInterview.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Delete an interview by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInterview(@PathVariable String id) {
        boolean isDeleted = interviewService.deleteInterview(id);
        if (isDeleted) {
            return ResponseEntity.ok("Interview with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST: Join the waiting room for a specific interview.
     *
     * @param interviewId The ID of the interview to join.
     * @return A response indicating the status of the operation.
     */
    @PostMapping("/joinWaitingRoom")
    public ResponseEntity<ResponseDto<String>> joinWaitingRoom(@RequestParam Long interviewId) {
            interviewService.joinWaitingRoom(interviewId);
            return ResponseEntity.ok(new ResponseDto<>("Successfully joined the waiting room.", HttpStatus.OK));
    }



    @PutMapping("/change-status")
    public ResponseEntity<ResponseDto<ChangeInterviewStatusResponseDto>> changeInterviewStatus(
            @Valid @RequestBody ChangeInterviewStatusRequestDto requestDto) {

        ChangeInterviewStatusResponseDto responseData = interviewService.changeInterviewStatus(requestDto);

        ResponseDto<ChangeInterviewStatusResponseDto> response = new ResponseDto<>(
                responseData,
                HttpStatusCode.valueOf(200),
                "Interview status updated successfully"
        );
        return ResponseEntity.ok(response);
    }


    @PostMapping("/feedback")
    public ResponseEntity<ResponseDto<String>> submitFeedback(@RequestBody FeedbackRequestDTO feedbackRequest) {
            interviewFeedbackService.handleFeedback(feedbackRequest);
            return ResponseEntity.ok(new ResponseDto<>("Success" , HttpStatus.OK));
    }
}