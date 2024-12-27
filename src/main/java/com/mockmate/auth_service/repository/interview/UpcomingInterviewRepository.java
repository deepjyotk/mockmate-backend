package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UpcomingInterviewRepository extends JpaRepository<UpcomingInterviews, Long> {
    // Additional query methods can be added here if needed
    List<UpcomingInterviews> findBySlotId(Long slotId);
    UpcomingInterviews findByUpcomingInterviewId(Long upcomingInterviewId);

    List<UpcomingInterviews> findByUserId(Long userId);

    boolean existsBySlotIdAndUserId(Long slotId, Long userId);

    Optional<UpcomingInterviews> findByUserIdAndRoomIDHash(Long userId, String roomIDHash);


}