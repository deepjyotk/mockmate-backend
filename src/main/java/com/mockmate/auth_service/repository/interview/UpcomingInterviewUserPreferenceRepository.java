package com.mockmate.auth_service.repository.interview;
import com.mockmate.auth_service.entities.interview.UpcomingInterviewUserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UpcomingInterviewUserPreferenceRepository extends JpaRepository<UpcomingInterviewUserPreference, Long> {
    // Additional query methods can be added here if needed


    List<UpcomingInterviewUserPreference> findByUpcomingInterview_UpcomingInterviewIdIn(List<Long> upcomingInterviewIds);
    Optional<UpcomingInterviewUserPreference> findByUpcomingInterview_UpcomingInterviewId(Long upcomingInterviewId);
    void deleteByUpcomingInterview_UpcomingInterviewId(Long upcomingInterviewId);

}