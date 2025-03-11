package com.mockmate.auth_service.service.otp;

public interface OTPService {

    /**
     * Generates a new OTP for the given email address and sends it via AWS SES (through JavaMailSender).
     * @param email the email address to send the OTP to
     */
    void generateAndSendOTP(String email);

    /**
     * Verifies the OTP for the given email address.
     * @param email the email address associated with the OTP
     * @param otp the OTP to verify
     * @return true if the OTP is valid and matches the stored OTP for that email, false otherwise
     */
    boolean verifyOTP(String email, String otp);
}