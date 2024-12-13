package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.InterviewFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewFeedbackRepository extends JpaRepository<InterviewFeedback, Long> {

}