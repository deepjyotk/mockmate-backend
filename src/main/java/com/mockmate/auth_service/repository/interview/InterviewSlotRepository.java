package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.InterviewSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewSlotRepository extends JpaRepository<InterviewSlot, Long> {
    @Query("SELECT s FROM InterviewSlot s JOIN FETCH s.interviewTypeTime t JOIN FETCH t.interviewType")
    List<InterviewSlot> findAllWithInterviewType();

    Optional<InterviewSlot> findBySlotId(Long slotId);

}

