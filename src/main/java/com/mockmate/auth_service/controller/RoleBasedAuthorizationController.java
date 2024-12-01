package com.mockmate.auth_service.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checking_authorization")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Role-Based Authorization", description = "Endpoints to demonstrate role-based access control.")
public class RoleBasedAuthorizationController {

    @Operation(
            summary = "Access for FREE_USER role",
            description = "Endpoint accessible only by users with the FREE_USER role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Access granted: FREE_USER"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have the required role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication is required")
    })
    @GetMapping("/free")
    @PreAuthorize("hasRole('FREE_USER')")
    public ResponseEntity<String> accessForFreeUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // userId from token
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok("Access granted: FREE_USER");
    }

    @Operation(
            summary = "Access for SUBSCRIBED_USER role",
            description = "Endpoint accessible only by users with the SUBSCRIBED_USER role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Access granted: SUBSCRIBED_USER"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have the required role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication is required")
    })
    @GetMapping("/subscribed")
    @PreAuthorize("hasRole('SUBSCRIBED_USER')")
    public ResponseEntity<String> accessForSubscribedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // userId from token
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // First role
        return ResponseEntity.ok("Access granted: SUBSCRIBED_USER");
    }

    @Operation(
            summary = "Access for ADMIN role",
            description = "Endpoint accessible only by users with the ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Access granted: ADMIN"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have the required role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication is required")
    })
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> accessForAdmin() {
        return ResponseEntity.ok("Access granted: ADMIN");
    }
}
