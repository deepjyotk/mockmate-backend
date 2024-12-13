package com.mockmate.auth_service.service.interview_feedback;

import com.mockmate.auth_service.dto.feedback.FeedbackRequestDTO;

public interface InterviewFeedbackService {
    void handleFeedback(FeedbackRequestDTO feedbackRequest);

}
