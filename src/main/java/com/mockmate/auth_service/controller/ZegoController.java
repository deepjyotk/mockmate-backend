package com.mockmate.auth_service.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/zego")
public class ZegoController {

    @Value("${zego.app-id}")
    private int appId;

    @Value("${zego.server-secret}")
    private String serverSecret;

    @Operation(
            summary = "Generate Kit Token",
            description = "Generates a Zego Kit Token for authenticated users to join a room.",
            tags = {"Zego"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated kit token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/generate-token")
    public Map<String, String> generateKitToken(@RequestParam String roomId, @RequestParam String userId, @RequestParam String userName) {
        // Retrieve authenticated user details
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication != null ? authentication.getName() : null;

        if (authenticatedUserId == null || !authenticatedUserId.equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        // Generate the kit token
        String kitToken = generateZegoKitToken(appId, serverSecret, roomId, userId, userName);

        // Create response
        Map<String, String> response = new HashMap<>();
        response.put("kitToken", kitToken);
        return response;
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