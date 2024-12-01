package com.mockmate.auth_service.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
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
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // Constructors
    public UserEntity(Long id, String username, String email, String passwordHash, LocalDateTime createdAt,
                      LocalDateTime updatedAt, LocalDateTime lastLoginAt, Boolean isEmailVerified,
                      String emailVerificationToken, String passwordResetToken, LocalDateTime passwordResetExpiresAt,
                      String firstName, String lastName, LocalDateTime dateOfBirth, String avatarUrl,
                      String universityMajor, String universityName, List<String> rolesTargeting,
                      int relevantWorkExperience, String language, String timezone, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
        this.isEmailVerified = isEmailVerified;
        this.emailVerificationToken = emailVerificationToken;
        this.passwordResetToken = passwordResetToken;
        this.passwordResetExpiresAt = passwordResetExpiresAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.avatarUrl = avatarUrl;
        this.universityMajor = universityMajor;
        this.universityName = universityName;
        this.rolesTargeting = rolesTargeting;
        this.relevantWorkExperience = relevantWorkExperience;
        this.language = language;
        this.timezone = timezone;
        this.roles = roles;
    }

    public UserEntity() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetExpiresAt() {
        return passwordResetExpiresAt;
    }

    public void setPasswordResetExpiresAt(LocalDateTime passwordResetExpiresAt) {
        this.passwordResetExpiresAt = passwordResetExpiresAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUniversityMajor() {
        return universityMajor;
    }

    public void setUniversityMajor(String universityMajor) {
        this.universityMajor = universityMajor;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public List<String> getRolesTargeting() {
        return rolesTargeting;
    }

    public void setRolesTargeting(List<String> rolesTargeting) {
        this.rolesTargeting = rolesTargeting;
    }

    public int getRelevantWorkExperience() {
        return relevantWorkExperience;
    }

    public void setRelevantWorkExperience(int relevantWorkExperience) {
        this.relevantWorkExperience = relevantWorkExperience;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Equals, hashCode, and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (!id.equals(that.id)) return false;
        if (!username.equals(that.username)) return false;
        if (!email.equals(that.email)) return false;
        return passwordHash.equals(that.passwordHash);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + passwordHash.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastLoginAt=" + lastLoginAt +
                ", isEmailVerified=" + isEmailVerified +
                ", emailVerificationToken='" + emailVerificationToken + '\'' +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                ", passwordResetExpiresAt=" + passwordResetExpiresAt +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", universityMajor='" + universityMajor + '\'' +
                ", universityName='" + universityName + '\'' +
                ", rolesTargeting=" + rolesTargeting +
                ", relevantWorkExperience=" + relevantWorkExperience +
                ", language='" + language + '\'' +
                ", timezone='" + timezone + '\'' +
                ", roles=" + roles +
                '}';
    }
}
