package com.mockmate.auth_service.controller;

import com.mockmate.auth_service.dto.ResponseDto;
import com.mockmate.auth_service.dto.interview.InitResponseDto;
import com.mockmate.auth_service.dto.user.UserSpecificResponseDto;
import com.mockmate.auth_service.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sample-get")
    public String getCurrentUser() {
        // Logic to return current user details
        return "User details";
    }

    @GetMapping("/init_data")
    public ResponseEntity<ResponseDto<InitResponseDto>> getInitData() {
        return ResponseEntity.ok( new ResponseDto<>(userService.getInitData(), HttpStatus.OK)) ;

    }

    @GetMapping("/specific")
    public ResponseEntity<ResponseDto<UserSpecificResponseDto>> getUserSpecificData(    @RequestParam(defaultValue = "1") int currentPage,
                                                                                        @RequestParam(defaultValue = "10") int limit){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // userId from token
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok( new ResponseDto<>(userService.getUserSpecificData(Long.parseLong(userId),currentPage, limit), HttpStatus.OK)) ;
    }
}
