package com.mockmate.auth_service.repository.question;

import com.mockmate.auth_service.entities.questions.QTag;
import com.mockmate.auth_service.entities.questions.QuestionTagCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionTagComboRepository extends JpaRepository<QuestionTagCombo, Long> {
    List<QuestionTagCombo> findByQuestionQuestionId(Long questionId);

}