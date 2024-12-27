package com.mockmate.auth_service.utils.mapper;

import com.mockmate.auth_service.dto.interview.InterviewTypeDTO;
import com.mockmate.auth_service.entities.interview.InterviewType;

public class InterviewTypeMapper {

    /**
     * Maps an InterviewType entity to an InterviewTypeDTO.
     *
     * @param interviewType the InterviewType entity to map from
     * @return the mapped InterviewTypeDTO object
     */
    public static InterviewTypeDTO toDTO(InterviewType interviewType) {
        if (interviewType == null) {
            return null;
        }
        return new InterviewTypeDTO(
                interviewType.getId(),
                interviewType.getType(),
                interviewType.getDescription(),
                interviewType.getDurationMinutes() != null ? interviewType.getDurationMinutes() : 0
        );
    }
}

