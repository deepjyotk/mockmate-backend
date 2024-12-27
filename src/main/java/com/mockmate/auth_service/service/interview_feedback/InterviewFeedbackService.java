package com.mockmate.auth_service.service.interview_feedback;

import com.mockmate.auth_service.dto.feedback.FeedbackRequestDTO;
import com.mockmate.auth_service.entities.interview.PastInterviews;
import org.springframework.data.util.Pair;

import java.util.Optional;

public interface InterviewFeedbackService {
    void handleFeedback(FeedbackRequestDTO feedbackRequest);
    Pair<PastInterviews, Optional<PastInterviews>> createPastFromUpcomingForPairs(Long interviewID) ;

}
