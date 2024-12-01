package com.mockmate.auth_service.service.question;

import com.mockmate.auth_service.dto.question.QuestionPopulateRequestDto;
import com.mockmate.auth_service.dto.question.QuestionResponseDto;

public interface QuestionService {
     QuestionResponseDto populateQuestion(QuestionPopulateRequestDto requestDTO);
     QuestionResponseDto getQuestionById(Long questionId);

     /*
          Generates random question:
          1. gets userId from interviewId.
          2. Find Random QuestionID with difficultyLevel = interviewTypeId.DifficultyLevel
          3.  Check if the user was already asked the same question in the pastInterview table
          4. If user was already asked that question, repeat process 2 and 3.
      */
     Long getRandomQuestionNotOccuredInPast(Long interviewId, Long interviewTypeId ) ;
}
