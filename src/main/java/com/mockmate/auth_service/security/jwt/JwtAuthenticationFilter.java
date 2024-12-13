package com.mockmate.auth_service.security.jwt;

import com.mockmate.auth_service.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtTokenUtil jwtUtils;

    @Autowired
    private final CustomUserDetailsService customUserDetailsService;


    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/users/sample-get",
            "/api/auth/register",
            "/api/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/error"
            // Add other public endpoints as needed
    );

    public JwtAuthenticationFilter(JwtTokenUtil jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isExcluded(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
//                String username = jwtUtils.getUsernameFromToken(token);
                Long userId = jwtUtils.getUserIdFromToken(token) ;

                // Generate the request ID
                String requestId = UUID.randomUUID().toString();

                // Add it to the request attributes
                request.setAttribute("requestId", requestId);

                // Optionally, add it to the response headers for client visibility
                response.addHeader("X-Request-ID", requestId);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Checks if the given path is excluded from JWT processing.
     *
     * @param path the request URI
     * @return true if the path is excluded, false otherwise
     */
    private boolean isExcluded(String path) {
        // You can use AntPathMatcher for pattern matching if needed
        for (String excludedPath : EXCLUDED_PATHS) {
            if (path.matches(excludedPath.replace("**", ".*"))) {
                return true;
            }
        }
        return false;
    }
}