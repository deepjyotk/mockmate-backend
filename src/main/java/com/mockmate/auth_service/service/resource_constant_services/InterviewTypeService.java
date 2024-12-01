package com.mockmate.auth_service.service.resource_constant_services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockmate.auth_service.dto.interview.InterviewTypeDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class InterviewTypeService {
    @Value("classpath:interview/interview-types.json")
    private Resource resource;

    private List<InterviewTypeDto> interviewTypes;

    @PostConstruct
    public void loadInterviewTypes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.interviewTypes = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load interview-types.json", e);
        }
    }

    public List<InterviewTypeDto> getInterviewTypes() {
        return interviewTypes;
    }
}
