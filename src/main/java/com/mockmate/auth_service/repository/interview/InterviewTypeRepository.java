package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.InterviewType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewTypeRepository extends JpaRepository<InterviewType, Long> {
}