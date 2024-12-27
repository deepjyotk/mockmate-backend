package com.mockmate.auth_service.config;

import com.mockmate.auth_service.security.jwt.JwtAuthenticationFilter;
import com.mockmate.auth_service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;





    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with custom configuration
                .cors(Customizer.withDefaults())


                // Disable CSRF (not needed for APIs)
                .csrf(csrf -> csrf.disable())


                // Set session management to stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                // Set endpoint-specific permissions
                .authorizeHttpRequests(auth -> auth
                        // Publicly accessible endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/users/sample-get").permitAll()
                        .requestMatchers("/api/room/changeRole").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/test/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/error").permitAll()


                        // Role-based access control
                        .requestMatchers("/api/checking_authorization/free").hasRole("FREE_USER")
                        .requestMatchers("/api/checking_authorization/subscribed").hasRole("SUBSCRIBED_USER")
                        .requestMatchers("/api/checking_authorization/admin").hasRole("ADMIN")


                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )


                // Add custom authentication provider
                .authenticationProvider(authenticationProvider())


                // Add JWT token filter before the default authentication filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    // AuthenticationManager to support manual authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // Configure Authentication Provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());


        return authProvider;
    }


    // Password encoder for hashing passwords
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // CORS configuration for cross-origin requests
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:9090","http://room-ws-service:9090", "http://localhost:3000", "http://room-ws-service-1:9090")); // Replace with production domains
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*")); // Allow all methods
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization")); // Expose token header


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

