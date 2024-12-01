package com.mockmate.auth_service.repository.question;

import com.mockmate.auth_service.entities.questions.QTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QTagRepository extends JpaRepository<QTag, Long> {
    Optional<QTag> findByQuestionTagText(String questionTagText);
    Optional<QTag> findByTagId(Long tagId);
}