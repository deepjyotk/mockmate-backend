package com.mockmate.auth_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mockmate.auth_service.dto.ResponseDto;
import com.mockmate.auth_service.dto.question.CompanyFrequencyDto;
import com.mockmate.auth_service.dto.question.QTagDto;
import com.mockmate.auth_service.dto.question.QuestionPopulateRequestDto;
import com.mockmate.auth_service.dto.question.QuestionResponseDto;
import com.mockmate.auth_service.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/questions")

public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/populateQuestionDB")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<QuestionResponseDto>> populateQuestion(
        @RequestParam("interviewTypeId") Long interviewTypeId,
       @RequestParam("questionDifficultyId") Long questionDifficultyId,

       @RequestParam("questionTitle") String questionTitle,
       @RequestParam("tags") String tags, // JSON String
       @RequestParam("companies") String companiesJson, // JSON String
       @RequestParam("markdownFile") MultipartFile markdownFile) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<QTagDto> tagsList;
        List<CompanyFrequencyDto> companiesList;


        tagsList = objectMapper.readValue(tags, new TypeReference<List<QTagDto>>() {});
        companiesList = objectMapper.readValue(companiesJson, new TypeReference<List<CompanyFrequencyDto>>() {});

        // Process the request using parsed values
        QuestionPopulateRequestDto requestDTO = new QuestionPopulateRequestDto(interviewTypeId, questionDifficultyId, questionTitle, tagsList, companiesList, markdownFile);

        QuestionResponseDto responseDTO = questionService.populateQuestion(requestDTO);

        ResponseDto<QuestionResponseDto> response = new ResponseDto<>(
                responseDTO,
                HttpStatus.OK,
                "Question populated successfully"
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{questionId}")
    public ResponseEntity<ResponseDto<QuestionResponseDto>> getQuestionById(@PathVariable Long questionId) {
        QuestionResponseDto responseDto = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(new ResponseDto<>(responseDto, HttpStatus.OK));
    }
}
