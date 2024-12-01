package com.mockmate.auth_service.repository.question;

import com.mockmate.auth_service.entities.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByInterviewLevel_Id(Long interviewLevelId);
    List<Question> findAllByInterviewLevel_IdAndInterviewType_Id(Long interviewLevelId, Long interviewTypeId);

    @Query(value = "SELECT * FROM questions_db ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();

}
