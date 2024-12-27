package com.mockmate.auth_service.service.resource_constant_services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockmate.auth_service.dto.interview.InterviewLevelDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class InterviewLevelService {
    @Value("classpath:interview/interview-levels.json")
    private Resource resource;

    private List<InterviewLevelDto> interviewLevels;

    @PostConstruct
    public void loadInterviewTypes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.interviewLevels = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load interview-types.json", e);
        }
    }

    public List<InterviewLevelDto> getInterviewLevels() {
        return interviewLevels;
    }
}
