package com.mockmate.auth_service.config;

// src/main/java/com/mockmate/auth_service/config/WebSocketHandshakeInterceptor.java

import com.mockmate.auth_service.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenUtil jwtTokenProvider;

    @Autowired
    public WebSocketHandshakeInterceptor(JwtTokenUtil jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        String query = request.getURI().getQuery();
        System.out.println("Query: " + query); // Debug the query string

        String token = null;
        if (query != null && query.contains("token=")) {
            token = query.substring(query.indexOf("token=") + 6);
            System.out.println("Token extracted: " + token);
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            System.out.println("User ID from token: " + userId);
            attributes.put("userId", userId);
            return true;
        }

        System.out.println("Invalid or missing token");
        return false; // Reject handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // No action needed after handshake
    }
}

