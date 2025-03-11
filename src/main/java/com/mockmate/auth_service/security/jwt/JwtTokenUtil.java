package com.mockmate.auth_service.security.jwt;

import com.mockmate.auth_service.entities.UserEntity;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final String JWT_SECRET = "ijlvUVCzHsYRYhsbfuo+5Ua+BGI1BxWegdoLBZnXorZsiNZKxNJjsSCrWBtBQrD9ppsAOLVRpBlu5L3U7p09eA==";
    private final int JWT_EXPIRATION_MS = 86400000; // 24 hours

    // Generate JWT Token
    public String generateToken(UserEntity user) {
        logger.info("Generating JWT token for user: {}", user.getUsername());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getRoles().stream().map(role -> role.getName()).toList());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        logger.debug("Generated JWT token: {}", token);
        return token;
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
            logger.info("Validating JWT token.");
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            logger.info("JWT token is valid.");
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("JWT token is malformed: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.warn("JWT token signature validation failed: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("JWT token argument is invalid: {}", e.getMessage());
        }
        return false;
    }

    // Get Username from JWT
    public String getUsernameFromToken(String token) {
        logger.info("Extracting username from JWT token.");
        String username = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        logger.debug("Extracted username: {}", username);
        return username;
    }

    // Get User ID from JWT
    public Long getUserIdFromToken(String token) {
        logger.info("Extracting user ID from JWT token.");
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        Long userId = claims.get("id", Long.class);
        logger.debug("Extracted user ID: {}", userId);
        return userId;
    }

    // Get Roles from JWT
    public List<String> getRolesFromToken(String token) {
        logger.info("Extracting roles from JWT token.");
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = claims.get("roles", List.class);
        logger.debug("Extracted roles: {}", roles);
        return roles;
    }
}