package com.mockmate.auth_service.repository.question;

import com.mockmate.auth_service.entities.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByInterviewLevel_Id(Long interviewLevelId);
    List<Question> findAllByInterviewLevel_IdAndInterviewType_Id(Long interviewLevelId, Long interviewTypeId);

    @Query(value = "SELECT * FROM questions_db ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Question> findRandomQuestion();



}
