package com.mockmate.auth_service.repository.interview;

import com.mockmate.auth_service.entities.interview.PastInterviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PastInterviewRepository extends JpaRepository<PastInterviews, Long> {
    // Additional query methods can be added here if needed
    List<PastInterviews> findAllByUserId(Long userId);

    @Query("SELECT p.questionId FROM PastInterviews p WHERE p.userId = :userId AND p.interviewType.id = :interviewTypeId")
    List<Long> findAllQuestionsByUserIdAndInterviewTypeId(@Param("userId") Long userId, @Param("interviewTypeId") Long interviewTypeId);



    boolean existsByUserIdAndQuestionId(Long userId, Long questionId);


    Page<PastInterviews> findByUserIdOrderByDateAndTimeDesc(Long userId, Pageable pageable);

}