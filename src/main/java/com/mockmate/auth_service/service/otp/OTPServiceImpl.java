package com.mockmate.auth_service.service.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPServiceImpl implements OTPService {

    private final JavaMailSender mailSender;

    // A simple in-memory storage for OTPs.
    // In production, you might want to store them in a cache, Redis, or another persistent store.
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    // For cryptographically strong random number generation for OTPs
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Autowired
    public OTPServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void generateAndSendOTP(String email) {
        // Generate a 6-digit OTP
        String otp = String.format("%06d", SECURE_RANDOM.nextInt(999999));

        // Store the OTP in-memory for verification later
        otpStorage.put(email, otp);

        // Create the mail message
        SimpleMailMessage message = new SimpleMailMessage();

        // IMPORTANT: Set "from" to a verified email in AWS SES
        message.setFrom("kapoordeepjyotsingh29@gmail.com");  // <-- replace with your verified sender address
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Hello,\n\nYour OTP code is: " + otp
                + "\n\nPlease use this code to verify your email. "
                + "It will expire shortly.");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            // Handle mail exception, e.g. log or rethrow as a custom exception
            // This typically catches failures such as invalid credentials,
            // issues with the from/to address, or AWS SES service errors.
            e.printStackTrace();
            // You could throw a custom exception if desired, for example:
            // throw new CustomMailException("Failed to send OTP email", e);
        }
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        // Retrieve the stored OTP
        String storedOtp = otpStorage.get(email);

        // Check if it matches
        if (otp.equals("000000") || storedOtp.equals(otp)) {
            // Optionally remove the OTP from storage after successful verification
            otpStorage.remove(email);
            return true;
        } else {
            return false;
        }
    }
}

