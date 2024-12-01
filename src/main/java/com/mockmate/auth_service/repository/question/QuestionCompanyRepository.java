package com.mockmate.auth_service.repository.question;

import com.mockmate.auth_service.entities.questions.QuestionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCompanyRepository extends JpaRepository<QuestionCompany, Long> {
}