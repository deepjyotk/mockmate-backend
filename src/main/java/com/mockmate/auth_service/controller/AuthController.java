package com.mockmate.auth_service.controller;
import com.mockmate.auth_service.dto.ResponseDto;
import com.mockmate.auth_service.dto.login.LoginRequestDto;
import com.mockmate.auth_service.dto.login.LoginResponseDto;
import com.mockmate.auth_service.dto.register.RegisterRequestDto;
import com.mockmate.auth_service.dto.register.RegisterResponseDto;
import com.mockmate.auth_service.service.user_service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user",
            description = "Register a new user with a username, email, password, and role. Returns a JWT token if registration is successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Conflict - Username or email already exists",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<RegisterResponseDto>> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto registerResponseDto = userService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(registerResponseDto, HttpStatus.CREATED));
    }

    @Operation(summary = "User login",
            description = "Login with a valid username and password. Returns a JWT token if the credentials are correct.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Login failed due to server issues",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(new ResponseDto<>(loginResponseDto, HttpStatus.OK));
    }
}
