package com.mockmate.auth_service.service.zego;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZegoServiceImpl implements ZegoService {

    @Value("${zego.app-id}")
    private int appId;

    @Value("${zego.server-secret}")
    private String serverSecret;

    @Override
    public String generateKitToken(String roomId, String userId, String userName) {
        // Retrieve authenticated user details
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication != null ? authentication.getName() : null;

        if (authenticatedUserId == null || !authenticatedUserId.equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        // Generate the kit token
        return generateZegoKitToken(appId, serverSecret, roomId, userId, userName);
    }

    private String generateZegoKitToken(int appId, String serverSecret, String roomId, String userId, String userName) {
        long expiration = Instant.now().getEpochSecond() + 3600;

        Map<String, Object> payload = new HashMap<>();
        payload.put("app_id", appId);
        payload.put("room_id", roomId);
        payload.put("user_id", userId);
        payload.put("user_name", userName);
        payload.put("exp", expiration);

        return Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, serverSecret.getBytes())
                .compact();
    }
}
