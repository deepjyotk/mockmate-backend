package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewStatusRepository extends JpaRepository<InterviewStatus, Long> {
    Optional<InterviewStatus> findByStatusName(String statusName);


}