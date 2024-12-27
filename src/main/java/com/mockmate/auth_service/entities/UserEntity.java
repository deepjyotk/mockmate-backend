package com.mockmate.auth_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified = false;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "password_reset_expires_at")
    private LocalDateTime passwordResetExpiresAt;

    // Profile details
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String avatarUrl;

    // New fields for peer-to-peer matching
    @Column(name = "university_major", nullable = false)
    private String universityMajor;

    @Column(name = "university_name", nullable = false)
    private String universityName;

    @ElementCollection
    @CollectionTable(name = "roles_targeting", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> rolesTargeting;

    @Column(name = "relevant_work_experience", nullable = false)
    private int relevantWorkExperience;

    // Settings
    @Column(name = "language", nullable = false)
    private String language = "en";

    @Column(name = "timezone", nullable = false)
    private String timezone = "UTC";

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "roles", nullable = false)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
