package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.InterviewTypeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewTypeTimeRepository extends JpaRepository<InterviewTypeTime, Long> {}
