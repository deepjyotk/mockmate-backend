package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.InterviewLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewLevelRepository extends JpaRepository<InterviewLevel, Long> {
}
