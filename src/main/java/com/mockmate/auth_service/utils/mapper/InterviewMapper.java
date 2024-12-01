package com.mockmate.auth_service.utils.mapper;

import com.mockmate.auth_service.dto.interview.UpcomingInterviewsDto;
import com.mockmate.auth_service.dto.interview.InterviewLevelDto;
import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import com.mockmate.auth_service.entities.interview.InterviewLevel;
import com.mockmate.auth_service.entities.interview.InterviewType;

public class InterviewMapper {

    // Convert Entity to DTO
    public static UpcomingInterviewsDto toDto(UpcomingInterviews entity) {
        if (entity == null) {
            return null;
        }

        UpcomingInterviewsDto dto = new UpcomingInterviewsDto();
        dto.setId(entity.getUpcomingInterviewId() != null ? entity.getUpcomingInterviewId().toString() : null);
        dto.setInterviewSlotId(entity.getSlotId() != null ? entity.getSlotId().toString() : null);
        dto.setUserId(entity.getUserId() != null ? entity.getUserId().toString() : null);

        // Assuming `status` has an ID that can be mapped to `interviewTypeId` or a similar property
        if (entity.getStatus() != null) {
            dto.setInterviewTypeId(entity.getStatus().getId() != null ? entity.getStatus().getId().toString() : null);
        }

        return dto;
    }

    // Convert DTO to Entity
    public static UpcomingInterviews toEntity(UpcomingInterviewsDto dto) {
        if (dto == null) {
            return null;
        }

        UpcomingInterviews entity = new UpcomingInterviews();
        entity.setUpcomingInterviewId(dto.getId() != null ? Long.valueOf(dto.getId()) : null);
        entity.setSlotId(dto.getInterviewSlotId() != null ? Long.valueOf(dto.getInterviewSlotId()) : null);
        entity.setUserId(dto.getUserId() != null ? Long.valueOf(dto.getUserId()) : null);

        // Setting a default status or leaving it null, depending on your application logic
        // For example, if the status should be retrieved or created elsewhere:
        // entity.setStatus(new InterviewStatus(Long.valueOf(dto.getInterviewTypeId())));

        return entity;
    }

    public static InterviewLevelDto toInterviewLevelDto(InterviewLevel interviewLevel) {
        if (interviewLevel == null) {
            return null;
        }
        InterviewLevelDto dto = new InterviewLevelDto();
        dto.setId(interviewLevel.getId());
        dto.setLevel(interviewLevel.getLevel());
        dto.setDescription(interviewLevel.getDescription());
        return dto;
    }

    public static InterviewLevel toInterviewLevel(InterviewLevelDto dto) {
        if (dto == null) {
            return null;
        }
        InterviewLevel interviewLevel = new InterviewLevel();
        interviewLevel.setId(dto.getId());
        interviewLevel.setLevel(dto.getLevel());
        interviewLevel.setDescription(dto.getDescription());
        return interviewLevel;
    }
}