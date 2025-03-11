package com.mockmate.auth_service.security.jwt;

import com.mockmate.auth_service.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private final JwtTokenUtil jwtUtils;

    @Autowired
    private final CustomUserDetailsService customUserDetailsService;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/users/sample-get",
            "/api/auth/register",
            "/api/auth/request-otp",
            "/api/auth/verify-otp-and-register",
//            "/api/auth/logout",
            "/api/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/error",
            "/room/changeRole"
    );

    public JwtAuthenticationFilter(JwtTokenUtil jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        logger.info("Processing request: {}", requestURI);

        if (isExcluded(requestURI)) {
            logger.info("Skipping authentication for excluded path: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractTokenFromCookies(request);

        if (token == null) {
            logger.warn("No access token found in cookies for request: {}", requestURI);
        } else {
            logger.info("Token extracted from cookies for request: {}", requestURI);
            if (jwtUtils.validateToken(token)) {
                logger.info("Token is valid for request: {}", requestURI);
                Long userId = jwtUtils.getUserIdFromToken(token);
                String requestId = UUID.randomUUID().toString();
                request.setAttribute("requestId", requestId);
                response.addHeader("X-Request-ID", requestId);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("User authenticated successfully with userId: {}", userId);
            } else {
                logger.warn("Invalid token provided for request: {}", requestURI);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            logger.warn("No cookies present in the request.");
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            logger.info("CookieName: "+ cookie.getName() +" CookieValue: "+ cookie.getValue());
            if (cookie.getName().contains("accessToken")) {
                logger.info("Found access token in cookies.");
                return cookie.getValue();
            }
        }
        logger.warn("Access token not found in cookies.");
        return null;
    }

    private boolean isExcluded(String path) {
        boolean excluded = EXCLUDED_PATHS.stream().anyMatch(excludedPath -> pathMatcher.match(excludedPath, path));
        if (excluded) {
            logger.debug("Path {} is excluded from authentication.", path);
        }
        return excluded;
    }
}