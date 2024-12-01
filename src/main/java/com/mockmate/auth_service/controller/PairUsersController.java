package com.mockmate.auth_service.controller;

import com.mockmate.auth_service.service.pair_users_service.PairUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PairUsersController {
    @Autowired
    private PairUsersService pairUsersService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/pairUsersForUpcomingInterviewCron")
    public ResponseEntity<String> pairUsersForUpcomingInterviewCron() {
            pairUsersService.pairUsersForUpcomingInterviews();
            return ResponseEntity.ok("Users paired successfully.");
    }
}
