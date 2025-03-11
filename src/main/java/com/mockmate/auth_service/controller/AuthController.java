package com.mockmate.auth_service.controller;

import com.mockmate.auth_service.config.EnvironmentConfig;
import com.mockmate.auth_service.dto.ResponseDto;
import com.mockmate.auth_service.dto.login.LoginRequestDto;
import com.mockmate.auth_service.dto.login.LoginResponseDto;
import com.mockmate.auth_service.dto.register.RegisterRequestDto;
import com.mockmate.auth_service.dto.register.RegisterResponseDto;
import com.mockmate.auth_service.exception.custom.InvalidOTPException;
import com.mockmate.auth_service.security.jwt.JwtTokenUtil;
import com.mockmate.auth_service.service.otp.OTPService;
import com.mockmate.auth_service.service.user_service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final IUserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final OTPService otpService;

    @Autowired
    private EnvironmentConfig environmentConfig;


    @Autowired
    public AuthController(IUserService userService, JwtTokenUtil jwtTokenUtil, OTPService otpService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.otpService = otpService;
    }

    @PostMapping("/request-otp")
    public ResponseEntity<ResponseDto<String>> requestOTP(@RequestBody RegisterRequestDto registerRequestDto) {
        logger.info("Requesting OTP for email: {}", registerRequestDto.getEmail());
        boolean isRegistered = userService.usernameOrEmailExists(registerRequestDto.getEmail());

        if (isRegistered) {
            logger.warn("Email {} is already registered", registerRequestDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto<>("Email is already registered", HttpStatus.CONFLICT));
        }

        otpService.generateAndSendOTP(registerRequestDto.getEmail());
        logger.info("OTP sent successfully to email: {}", registerRequestDto.getEmail());
        return ResponseEntity.ok(new ResponseDto<>("OTP sent to email", HttpStatus.OK));
    }

    @PostMapping("/verify-otp-and-register")
    public ResponseEntity<ResponseDto<RegisterResponseDto>> verifyOtpAndRegister(
            @RequestBody RegisterRequestDto request, HttpServletResponse response) {

        logger.info("Verifying OTP for email: {}", request.getEmail());

        if (!otpService.verifyOTP(request.getEmail(), request.getOtp())) {
            logger.error("Invalid OTP for email: {}", request.getEmail());
            throw new InvalidOTPException();
        }

        RegisterResponseDto registerResponseDto = userService.register(request);
        String token = userService.generateJwtToken(request.getUsername());
        createJwtCookie(token, registerResponseDto.getUsername(), response);

        logger.info("User registered successfully for email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(registerResponseDto, HttpStatus.CREATED));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> loginUser(
            @Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        logger.info("User login attempt for: {}", loginRequestDto.getUserOrEmail());

        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);

        String token = userService.generateJwtToken(loginRequestDto.getUserOrEmail());
        createJwtCookie(token, loginResponseDto.getUsername() ,response);

        logger.info("User logged in successfully: {}", loginRequestDto.getUserOrEmail());
        return ResponseEntity.ok(new ResponseDto<>(loginResponseDto, HttpStatus.OK));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<String>> logoutUser(HttpServletResponse response) {
        logger.info("User logout attempt");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userID = authentication.getName();
        logger.info("userID is: "+ userID);
        String userName = userService.getUsername(Long.parseLong(userID) );
        logger.info("Username is: "+ userName);

        createJwtCookie(null, userName, response);
        logger.info("User logged out successfully");
        return ResponseEntity.ok(new ResponseDto<>("Logged out successfully", HttpStatus.OK));
    }

    private void createJwtCookie(String token, String username, HttpServletResponse response) {
        ResponseCookie cookie;
        String cookieKey = "accessToken_" + username;

        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(cookieKey, token != null ? token : "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None");

        if (token != null) {
            cookieBuilder.maxAge(7 * 24 * 60 * 60);  // Set expiration for 7 days if token exists
        } else {
            cookieBuilder.maxAge(0);  // Immediately expire cookie
        }

        if (!environmentConfig.isLocalProfile() && !environmentConfig.isRunningLocally()) {
            logger.info("Not running in LOCAL ENV, setting cookie domain");
            cookieBuilder.domain(".mockmate.live");
        }

        cookie = cookieBuilder.build();
        logger.info(token != null ? "JWT cookie created successfully." : "JWT cookie cleared successfully.");
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}