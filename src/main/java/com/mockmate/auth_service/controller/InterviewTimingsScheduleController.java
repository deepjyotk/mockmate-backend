package com.mockmate.auth_service.controller;

import com.mockmate.auth_service.service.interview_timings.InterviewScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class InterviewTimingsScheduleController {

    @Autowired
    private InterviewScheduleService scheduleService;

    @GetMapping("/api/generate-schedule")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Generate interview schedule",
            description = "Creates and inserts a new interview schedule. This endpoint can only be accessed by users with the ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interview schedule created successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have the ADMIN role"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Something went wrong while creating the schedule")
    })
    public String generateSchedule() {
        scheduleService.createAndInsertSchedule();
        return "Interview schedule created successfully!";
    }
}
